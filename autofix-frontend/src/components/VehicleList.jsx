import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import vehicleService from "../services/vehicle.service"; 
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
import AddIcon from '@mui/icons-material/Add';

const VehicleList = () => {
  const [vehicles, setVehicles] = useState([]);

  const navigate = useNavigate();

  const init = () => {
    vehicleService
      .getAll()
      .then((response) => {
        console.log("Mostrando listado de todos los vehículos.", response.data);
        setVehicles(response.data);
      })
      .catch((error) => {
        console.log("Se ha producido un error al mostrar los vehículos.", error);
      });
  };

  useEffect(() => {
    init();
  }, []);

  const handleDelete = (id) => {
    const confirmDelete = window.confirm("¿Está seguro que desea eliminar este vehículo?");
    if (confirmDelete) {
      vehicleService
        .remove(id)
        .then(() => {
          console.log("Vehículo eliminado.");
          init();
        })
        .catch((error) => {
          console.log("Error al eliminar el vehículo", error);
        });
    }
  };

  const handleEdit = (id) => {
    navigate(`/vehicles/edit/${id}`);
  };

  return (
    <TableContainer component={Paper}>
      <br />
      <Link to="/vehicles/create" style={{ textDecoration: "none" }}>
        <div className="card-content">
          <Button variant="contained" color="primary" startIcon={<AddIcon />}>
            Añadir Vehículo
          </Button>
        </div>
      </Link>
      <br /><br />
      <Table sx={{ minWidth: 650 }} size="small" aria-label="a dense table">
        <TableHead>
          <TableRow>
          <TableCell align="left" sx={{ fontWeight: "bold" }}>ID</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Patente</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Marca</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Modelo</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Tipo</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Año</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Motor</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Asientos</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Km</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Operaciones</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {vehicles.map((vehicle) => (
            <TableRow key={vehicle.id}>
              <TableCell align="left">{vehicle.id}</TableCell>
              <TableCell align="left">{vehicle.licensePlate}</TableCell>
              <TableCell align="left">{vehicle.brand}</TableCell>
              <TableCell align="left">{vehicle.model}</TableCell>
              <TableCell align="left">{vehicle.type}</TableCell>
              <TableCell align="left">{vehicle.yearOfManufacture}</TableCell>
              <TableCell align="left">{vehicle.engineType}</TableCell>
              <TableCell align="left">{vehicle.numberOfSeats}</TableCell>
              <TableCell align="left">{vehicle.mileage}</TableCell>
              <TableCell>
                <Button
                  variant="contained"
                  color="info"
                  size="small"
                  onClick={() => handleEdit(vehicle.id)}
                  startIcon={<EditIcon />}
                >
                  Editar
                </Button>
                <Button
                  variant="contained"
                  color="error"
                  size="small"
                  onClick={() => handleDelete(vehicle.id)}
                  startIcon={<DeleteIcon />}
                  style={{ marginLeft: "10px" }}
                >
                  Eliminar
                </Button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default VehicleList;