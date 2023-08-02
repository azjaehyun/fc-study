import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IApplicant } from 'app/shared/model/applicant.model';
import { getEntity, updateEntity, createEntity, reset } from './applicant.reducer';

export const ApplicantUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const applicantEntity = useAppSelector(state => state.applicant.entity);
  const loading = useAppSelector(state => state.applicant.loading);
  const updating = useAppSelector(state => state.applicant.updating);
  const updateSuccess = useAppSelector(state => state.applicant.updateSuccess);

  const handleClose = () => {
    navigate('/applicant' + location.search);
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
    const entity = {
      ...applicantEntity,
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
      ? {}
      : {
          ...applicantEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="applicationApp.applicant.home.createOrEditLabel" data-cy="ApplicantCreateUpdateHeading">
            <Translate contentKey="applicationApp.applicant.home.createOrEditLabel">Create or edit a Applicant</Translate>
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
                  name="applicantId"
                  required
                  readOnly
                  id="applicant-applicantId"
                  label={translate('applicationApp.applicant.applicantId')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('applicationApp.applicant.name')}
                id="applicant-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                label={translate('applicationApp.applicant.email')}
                id="applicant-email"
                name="email"
                data-cy="email"
                type="text"
              />
              <ValidatedField
                label={translate('applicationApp.applicant.phone')}
                id="applicant-phone"
                name="phone"
                data-cy="phone"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/applicant" replace color="info">
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

export default ApplicantUpdate;
