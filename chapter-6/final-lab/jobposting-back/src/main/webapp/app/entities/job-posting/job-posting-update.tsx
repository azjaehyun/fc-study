import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IJobPosting } from 'app/shared/model/job-posting.model';
import { getEntity, updateEntity, createEntity, reset } from './job-posting.reducer';

export const JobPostingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const jobPostingEntity = useAppSelector(state => state.jobPosting.entity);
  const loading = useAppSelector(state => state.jobPosting.loading);
  const updating = useAppSelector(state => state.jobPosting.updating);
  const updateSuccess = useAppSelector(state => state.jobPosting.updateSuccess);

  const handleClose = () => {
    navigate('/job-posting' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.postedDate = convertDateTimeToServer(values.postedDate);

    const entity = {
      ...jobPostingEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          postedDate: displayDefaultDateTime(),
        }
      : {
          ...jobPostingEntity,
          postedDate: convertDateTimeFromServer(jobPostingEntity.postedDate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jobpostingApp.jobPosting.home.createOrEditLabel" data-cy="JobPostingCreateUpdateHeading">
            <Translate contentKey="jobpostingApp.jobPosting.home.createOrEditLabel">Create or edit a JobPosting</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="jobId"
                  required
                  readOnly
                  id="job-posting-jobId"
                  label={translate('jobpostingApp.jobPosting.jobId')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jobpostingApp.jobPosting.title')}
                id="job-posting-title"
                name="title"
                data-cy="title"
                type="text"
              />
              <ValidatedField
                label={translate('jobpostingApp.jobPosting.description')}
                id="job-posting-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('jobpostingApp.jobPosting.location')}
                id="job-posting-location"
                name="location"
                data-cy="location"
                type="text"
              />
              <ValidatedField
                label={translate('jobpostingApp.jobPosting.postedDate')}
                id="job-posting-postedDate"
                name="postedDate"
                data-cy="postedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/job-posting" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default JobPostingUpdate;
