import React, { useState, useEffect } from 'react';
import { LocalizationProvider, DatePicker } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import dayjs from "dayjs";
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper } from '@mui/material';
import reportService from '../services/report.service';
import Box from '@mui/material/Box';

const MonthlyRepairComparisonReport = () => {
  const [date, setDate] = useState(dayjs());
  const [report, setReport] = useState([]);

  useEffect(() => {
    fetchMonthlyRepairComparisonReport(date.month() + 1, date.year());
  }, [date]);

  const fetchMonthlyRepairComparisonReport = (month, year) => {
    reportService.getMonthlyRepairComparisonReport(month, year)
      .then(response => {
        setReport(response.data);
      })
      .catch(error => {
        console.error('Error fetching monthly repair comparison report:', error);
      });
  };

  return (
    <div>
      <Box display="flex" flexDirection="column" alignItems="center" mb={2} sx={{ width: '100%', overflow: 'auto' }}>
      <br />
      <h2>Reporte de Comparación Mensual</h2>
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
              <TableCell>Month</TableCell>
              <TableCell>Repair Count</TableCell>
              <TableCell>Total Amount</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {report.map((row, index) => (
              <TableRow key={index}>
                <TableCell>{row.month}</TableCell>
                <TableCell>{row.repairCount}</TableCell>
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

export default MonthlyRepairComparisonReport;