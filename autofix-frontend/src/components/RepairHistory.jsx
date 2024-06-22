import { useEffect, useState } from "react";
import repairService from "../services/repair.service";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import Box from "@mui/material/Box";
import { styled } from "@mui/material/styles";

const ScrollableTableContainer = styled(TableContainer)({
  maxHeight: "calc(100vh - 200px)",
  overflow: "auto",
});

const RepairHistory = () => {
  const [repairHistory, setRepairHistory] = useState([]);

  const init = () => {
    repairService
      .getVehicleRepairHistory()
      .then((response) => {
        console.log("Mostrando historial de reparaciones.", response.data);
        setRepairHistory(response.data);
      })
      .catch((error) => {
        console.log("Se ha producido un error al mostrar el historial de reparaciones.", error);
      });
  };

  useEffect(() => {
    init();
  }, []);

  return (
    <Box display="flex" flexDirection="column" alignItems="center" mb={2} sx={{ width: '100%', overflow: 'auto' }}>
      <br />
      <h2>Reporte Histórico de Reparaciones</h2>
      <br />
      <ScrollableTableContainer component={Paper} sx={{ maxHeight: "calc(100vh - 200px)", overflow: "auto", width: '100%' }}>
        <Table stickyHeader sx={{ minWidth: 1200 }} size="small" aria-label="historial de reparaciones">
          <TableHead>
            <TableRow>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>Patente</TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>Marca</TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>Modelo</TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>Tipo</TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>Año de Fabricación</TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>Tipo de Motor</TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>Fecha de Entrada</TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>Hora de Entrada</TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>Monto de Reparación</TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>Monto de Recargo</TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>Monto de Descuento</TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>Monto de Impuesto</TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>Costo Total</TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>Fecha de Salida</TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>Hora de Salida</TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>Fecha de Recogida</TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>Hora de Recogida</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {repairHistory.map((repair, index) => (
              <TableRow key={index}>
                <TableCell align="left">{repair.licensePlate}</TableCell>
                <TableCell align="left">{repair.brand}</TableCell>
                <TableCell align="left">{repair.model}</TableCell>
                <TableCell align="left">{repair.type}</TableCell>
                <TableCell align="left">{repair.yearOfManufacture}</TableCell>
                <TableCell align="left">{repair.engineType}</TableCell>
                <TableCell align="left">{repair.entryDate}</TableCell>
                <TableCell align="left">{repair.entryTime}</TableCell>
                <TableCell align="left">{repair.totalRepairAmount}</TableCell>
                <TableCell align="left">{repair.surchargeAmount}</TableCell>
                <TableCell align="left">{repair.discountAmount}</TableCell>
                <TableCell align="left">{repair.taxAmount}</TableCell>
                <TableCell align="left">{repair.totalCost}</TableCell>
                <TableCell align="left">{repair.exitDate}</TableCell>
                <TableCell align="left">{repair.exitTime}</TableCell>
                <TableCell align="left">{repair.pickupDate}</TableCell>
                <TableCell align="left">{repair.pickupTime}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </ScrollableTableContainer>
    </Box>
  );
};

export default RepairHistory;