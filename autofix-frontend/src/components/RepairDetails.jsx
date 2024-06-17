import React, { useEffect, useState } from "react";
import { useParams, useNavigate, Link } from "react-router-dom";
import repairService from "../services/repair.service";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import Button from "@mui/material/Button";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import Box from "@mui/material/Box";
import AddIcon from "@mui/icons-material/Add";

const RepairDetails = () => {
  const [repairDetails, setRepairDetails] = useState([]);
  const { vehicleId } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    repairService
      .getRepairDetailsByVehicleId(vehicleId)
      .then((response) => {
        console.log("Mostrando detalles de reparación.", response.data);
        setRepairDetails(response.data);
      })
      .catch((error) => {
        console.log("Se ha producido un error al mostrar los detalles de la reparación.", error);
      });
  }, [vehicleId]);

  const handleEdit = (id) => {
    navigate(`/repair-details/edit/${id}`);
  };

  const handleDelete = (id) => {
    const confirmDelete = window.confirm("¿Está seguro que desea eliminar este detalle de reparación?");
    if (confirmDelete) {
      repairService
        .deleteRepairDetailById(id)
        .then(() => {
          console.log("Detalle de reparación eliminado.");
          setRepairDetails(repairDetails.filter((detail) => detail.id !== id));
        })
        .catch((error) => {
          console.log("Error al eliminar el detalle de reparación", error);
        });
    }
  };

  return (
    <TableContainer component={Paper}>
      <br />
      <Box display="flex" flexDirection="column" alignItems="center" mb={2}>
        <h2>Detalles de Reparación</h2>
        <Link to={`/repair-details/create/${vehicleId}`} style={{ textDecoration: "none" }}>
          <Button variant="contained" color="primary" startIcon={<AddIcon />}>
            Añadir Detalle
          </Button>
        </Link>
      </Box>
      {repairDetails.length === 0 ? (
        <Box display="flex" justifyContent="center" alignItems="center" minHeight="200px">
          <h3>Sin detalles de reparación</h3>
        </Box>
      ) : (
        <Table sx={{ minWidth: 650 }} size="small" aria-label="a dense table">
          <TableHead>
            <TableRow>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>ID Detalle</TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>ID Reparación</TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>Tipo de Reparación</TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>Fecha de Reparación</TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>Hora de Reparación</TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>Monto de Reparación</TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>Patente</TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>Operaciones</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {repairDetails.map((detail) => (
              <TableRow key={detail.id}>
                <TableCell align="left">{detail.id}</TableCell>
                <TableCell align="left">{detail.repairId}</TableCell>
                <TableCell align="left">{detail.repairType}</TableCell>
                <TableCell align="left">{detail.repairDate}</TableCell>
                <TableCell align="left">{detail.repairTime}</TableCell>
                <TableCell align="left">{detail.repairAmount}</TableCell>
                <TableCell align="left">{detail.licensePlate}</TableCell>
                <TableCell>
                  <Box display="flex" flexDirection="column" alignItems="center" justifyContent="center">
                    <Button
                      variant="contained"
                      color="info"
                      size="small"
                      onClick={() => handleEdit(detail.id)}
                      startIcon={<EditIcon />}
                      sx={{ mb: 1, width: '100%' }}
                    >
                      Editar
                    </Button>
                    <Button
                      variant="contained"
                      color="error"
                      size="small"
                      onClick={() => handleDelete(detail.id)}
                      startIcon={<DeleteIcon />}
                      sx={{ width: '100%' }}
                    >
                      Eliminar
                    </Button>
                  </Box>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      )}
    </TableContainer>
  );
};

export default RepairDetails;