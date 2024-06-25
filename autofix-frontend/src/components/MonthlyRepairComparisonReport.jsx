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

  const getMonthName = (month) => {
    return dayjs().month(month - 1).format("MMMM");
  };

  const currentMonthName = getMonthName(date.month() + 1);
  const firstPreviousMonthName = getMonthName(date.month());
  const secondPreviousMonthName = getMonthName(date.month() - 1);

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
                <TableCell>Repair Type</TableCell>
                <TableCell>{currentMonthName}</TableCell>
                <TableCell>Variation (%)</TableCell>
                <TableCell>{firstPreviousMonthName}</TableCell>
                <TableCell>Variation (%)</TableCell>
                <TableCell>{secondPreviousMonthName}</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {report.map((row, index) => (
                <React.Fragment key={index}>
                  <TableRow>
                    <TableCell>{row.repairType}</TableCell>
                    <TableCell>{row.repairsCountPerMonth1}</TableCell>
                    <TableCell>{row.variationPerMonth1.toFixed(2)}%</TableCell>
                    <TableCell>{row.repairsCountPerMonth2}</TableCell>
                    <TableCell>{row.variationPerMonth2.toFixed(2)}%</TableCell>
                    <TableCell>{row.repairsCountPerMonth3}</TableCell>
                  </TableRow>
                  <TableRow>
                    <TableCell></TableCell>
                    <TableCell>{row.repairsAmountPerMonth1.toLocaleString('es-CL', { style: 'currency', currency: 'CLP' })}</TableCell>
                    <TableCell>{row.variationAmountPerMonth1.toFixed(2)}%</TableCell>
                    <TableCell>{row.repairsAmountPerMonth2.toLocaleString('es-CL', { style: 'currency', currency: 'CLP' })}</TableCell>
                    <TableCell>{row.variationAmountPerMonth2.toFixed(2)}%</TableCell>
                    <TableCell>{row.repairsAmountPerMonth3.toLocaleString('es-CL', { style: 'currency', currency: 'CLP' })}</TableCell>
                  </TableRow>
                </React.Fragment>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </Box>
    </div>
  );
};

export default MonthlyRepairComparisonReport;