import React from 'react';
import {Resume} from "../react-applicant-common";
import { DataGrid, GridColDef } from '@mui/x-data-grid';

import { styled } from '@mui/material/styles';
import Paper from '@mui/material/Paper';
const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
    ...theme.typography.body2,
    padding: theme.spacing(1),
    textAlign: 'center',
    color: theme.palette.text.secondary,
  }));
const applicantColumns: GridColDef[] = [
    { field: 'resumeId', headerName: 'ID', width: 90 },
    {
      field: 'title',
      headerName: '이력서 제목',
      width: 180,
      editable: true,
    },
    {
      field: 'content',
      headerName: '이력서 내용',
      width: 300,
      editable: true,
    },
    {
      field: 'applicant',
      headerName: '지원자이름',
      type: 'string',
      width: 200,
      editable: true,
      valueGetter: (params) => params.row?.applicant?.name 
    },
    {
      field: 'submittedDate',
      headerName: '작성일자',
      type: 'string',
      width: 200,
      editable: true,
    }
  ];
  
const ApplicantGrid =  ({gridData}: {gridData: Resume[]}) => {
    

    return (
        <Item>
              <DataGrid
              getRowId={(row) => row.resumeId}
              rows={gridData}
              columns={applicantColumns}
              initialState={{
                pagination: {
                  paginationModel: {
                    pageSize: 4,
                  },
                },
              }}
              pageSizeOptions={[4]}
              checkboxSelection
              disableRowSelectionOnClick
              //isCellEditable={(params) => params.row.age % 2 === 0}
              />

        </Item>
    )

}

export default ApplicantGrid;