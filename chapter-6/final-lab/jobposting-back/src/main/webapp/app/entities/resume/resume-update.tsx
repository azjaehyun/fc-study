import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IResume } from 'app/shared/model/resume.model';
import { getEntity, updateEntity, createEntity, reset } from './resume.reducer';

export const ResumeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const resumeEntity = useAppSelector(state => state.resume.entity);
  const loading = useAppSelector(state => state.resume.loading);
  const updating = useAppSelector(state => state.resume.updating);
  const updateSuccess = useAppSelector(state => state.resume.updateSuccess);

  const handleClose = () => {
    navigate('/resume' + location.search);
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
    values.submittedDate = convertDateTimeToServer(values.submittedDate);

    const entity = {
      ...resumeEntity,
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
          submittedDate: displayDefaultDateTime(),
        }
      : {
          ...resumeEntity,
          submittedDate: convertDateTimeFromServer(resumeEntity.submittedDate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jobpostingApp.resume.home.createOrEditLabel" data-cy="ResumeCreateUpdateHeading">
            <Translate contentKey="jobpostingApp.resume.home.createOrEditLabel">Create or edit a Resume</Translate>
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
                  name="resumeId"
                  required
                  readOnly
                  id="resume-resumeId"
                  label={translate('jobpostingApp.resume.resumeId')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('jobpostingApp.resume.title')} id="resume-title" name="title" data-cy="title" type="text" />
              <ValidatedField
                label={translate('jobpostingApp.resume.content')}
                id="resume-content"
                name="content"
                data-cy="content"
                type="text"
              />
              <ValidatedField
                label={translate('jobpostingApp.resume.submittedDate')}
                id="resume-submittedDate"
                name="submittedDate"
                data-cy="submittedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/resume" replace color="info">
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

export default ResumeUpdate;
