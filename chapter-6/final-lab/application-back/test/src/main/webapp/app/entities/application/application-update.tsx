import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IResume } from 'app/shared/model/resume.model';
import { getEntities as getResumes } from 'app/entities/resume/resume.reducer';
import { IApplication } from 'app/shared/model/application.model';
import { getEntity, updateEntity, createEntity, reset } from './application.reducer';

export const ApplicationUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const resumes = useAppSelector(state => state.resume.entities);
  const applicationEntity = useAppSelector(state => state.application.entity);
  const loading = useAppSelector(state => state.application.loading);
  const updating = useAppSelector(state => state.application.updating);
  const updateSuccess = useAppSelector(state => state.application.updateSuccess);

  const handleClose = () => {
    navigate('/application' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getResumes({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.applicationDate = convertDateTimeToServer(values.applicationDate);

    const entity = {
      ...applicationEntity,
      ...values,
      resume: resumes.find(it => it.resumeId.toString() === values.resume.toString()),
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
          applicationDate: displayDefaultDateTime(),
        }
      : {
          ...applicationEntity,
          applicationDate: convertDateTimeFromServer(applicationEntity.applicationDate),
          resume: applicationEntity?.resume?.resumeId,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="applicationApp.application.home.createOrEditLabel" data-cy="ApplicationCreateUpdateHeading">
            <Translate contentKey="applicationApp.application.home.createOrEditLabel">Create or edit a Application</Translate>
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
                  name="applicationId"
                  required
                  readOnly
                  id="application-applicationId"
                  label={translate('applicationApp.application.applicationId')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('applicationApp.application.applicationDate')}
                id="application-applicationDate"
                name="applicationDate"
                data-cy="applicationDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="application-resume"
                name="resume"
                data-cy="resume"
                label={translate('applicationApp.application.resume')}
                type="select"
              >
                <option value="" key="0" />
                {resumes
                  ? resumes.map(otherEntity => (
                      <option value={otherEntity.resumeId} key={otherEntity.resumeId}>
                        {otherEntity.resumeId}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/application" replace color="info">
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

export default ApplicationUpdate;
