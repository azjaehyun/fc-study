import React from 'react';
import {JobPosting} from "../react-jobposting-common";
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

const jobColumns: GridColDef[] = [
  { field: 'jobId', headerName: 'ID', width: 90 },
  {
    field: 'title',
    headerName: '제목',
    width: 180,
    editable: true,
  },
  {
    field: 'description',
    headerName: '이력서내용',
    width: 300,
    editable: true,
  },
  {
    field: 'company',
    headerName: '등록 회사',
    type: 'string',
    width: 200,
    editable: true,
    valueGetter: (params) => params.row?.company?.name 
  },
  {
    field: 'postedDate',
    headerName: '작성일자',
    type: 'string',
    width: 200,
    editable: true
  },
];

  
const JobGrid =  ({gridData}: {gridData: JobPosting[]}) => {
    

    return (
      <Item>
          <DataGrid
          getRowId={(row) => row.jobId}
          rows={gridData}
          columns={jobColumns}
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

export default JobGrid;