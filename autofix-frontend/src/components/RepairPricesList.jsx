import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import repairPricesListService from "../services/repairPricesList.service";
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
import Box from "@mui/material/Box";

const RepairPricesList = () => {
  const [repairs, setRepairs] = useState([]);

  const navigate = useNavigate();

  const init = () => {
    repairPricesListService
      .getAll()
      .then((response) => {
        console.log("Mostrando listado de todas las reparaciones.", response.data);
        setRepairs(response.data);
      })
      .catch((error) => {
        console.log("Se ha producido un error al mostrar las reparaciones.", error);
      });
  };

  useEffect(() => {
    init();
  }, []);

  const handleDelete = (id) => {
    const confirmDelete = window.confirm("¿Está seguro que desea eliminar esta reparación?");
    if (confirmDelete) {
      repairPricesListService
        .remove(id)
        .then(() => {
          console.log("Reparación eliminada.");
          init();
        })
        .catch((error) => {
          console.log("Error al eliminar la reparación", error);
        });
    }
  };

  const handleEdit = (id) => {
    navigate(`/repair-list/edit/${id}`);
  };

  return (
    <TableContainer component={Paper}>
      <br />
      <Box display="flex" flexDirection="column" alignItems="center" mb={2}>
      <h2>Tipos de Reparaciones y sus Precios</h2>
        <Link to="/repair-list/create" style={{ textDecoration: "none" }}>
          <Button variant="contained" color="primary" startIcon={<AddIcon />}>
            Añadir Tipo Reparación
          </Button>
        </Link>
      </Box>
      <br />
      <Table sx={{ minWidth: 650 }} size="small" aria-label="a dense table">
        <TableHead>
          <TableRow>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Tipo de Reparación</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Precio Gasolina</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Precio Diésel</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Precio Híbrido</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Precio Eléctrico</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Operaciones</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {repairs.map((repair) => (
            <TableRow key={repair.id}>
              <TableCell align="left">{repair.repairType}</TableCell>
              <TableCell align="left">{repair.gasolinePrice}</TableCell>
              <TableCell align="left">{repair.dieselPrice}</TableCell>
              <TableCell align="left">{repair.hybridPrice}</TableCell>
              <TableCell align="left">{repair.electricPrice}</TableCell>
              <TableCell>
                <Button
                  variant="contained"
                  color="info"
                  size="small"
                  onClick={() => handleEdit(repair.id)}
                  startIcon={<EditIcon />}
                >
                  Editar
                </Button>
                <Button
                  variant="contained"
                  color="error"
                  size="small"
                  onClick={() => handleDelete(repair.id)}
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

export default RepairPricesList;