import React, { useState, useEffect } from 'react';
import { LocalizationProvider, DatePicker } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import dayjs from "dayjs";
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper } from '@mui/material';
import reportService from '../services/report.service';
import Box from '@mui/material/Box';

const RepairTypeReport = () => {
  const [date, setDate] = useState(dayjs());
  const [report, setReport] = useState([]);

  useEffect(() => {
    fetchRepairTypeReport(date.month() + 1, date.year());
  }, [date]);

  const fetchRepairTypeReport = (month, year) => {
    reportService.getRepairTypeReport(month, year)
      .then(response => {
        setReport(response.data);
      })
      .catch(error => {
        console.error('Error fetching repair type report:', error);
      });
  };

  return (
    <div>
      <Box display="flex" flexDirection="column" alignItems="center" mb={2} sx={{ width: '100%', overflow: 'auto' }}>
      <br />
      <h2>Reporte por Tipo de Reparación</h2>
      <div className="date-selector-container">
        <div className="date-selector">
          <p>Select Month and Year</p>
          <LocalizationProvider dateAdapter={AdapterDayjs}>
            <DatePicker
              views={['year', 'month']}
              label="Month and Year"
              value={date}
              onChange={(newValue) => setDate(newValue)}
              slotProps={{ textField: { fullWidth: true } }}
            />
          </LocalizationProvider>
        </div>
      </div>
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Repair Type</TableCell>
              <TableCell>Sedan Count</TableCell>
              <TableCell>Hatchback Count</TableCell>
              <TableCell>SUV Count</TableCell>
              <TableCell>Pickup Count</TableCell>
              <TableCell>Van Count</TableCell>
              <TableCell>Total Amount</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {report.map((row, index) => (
              <TableRow key={index}>
                <TableCell>{row.repairType}</TableCell>
                <TableCell>{row.sedanCount}</TableCell>
                <TableCell>{row.hatchbackCount}</TableCell>
                <TableCell>{row.suvCount}</TableCell>
                <TableCell>{row.pickupCount}</TableCell>
                <TableCell>{row.vanCount}</TableCell>
                <TableCell>{row.totalAmount}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
      </Box>
    </div>
  );
};

export default RepairTypeReport;