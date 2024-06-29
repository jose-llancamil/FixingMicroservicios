import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import repairService from "../services/repair.service";
import discountService from "../services/discount.service";
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
import InfoIcon from "@mui/icons-material/Info";
import Box from "@mui/material/Box";
import AddIcon from '@mui/icons-material/Add';
import DiscountIcon from '@mui/icons-material/Discount';
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";

const RepairList = () => {
  const [repairs, setRepairs] = useState([]);
  const [selectedRepair, setSelectedRepair] = useState(null);
  const [open, setOpen] = useState(false);
  const [discounts, setDiscounts] = useState([]);
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  const init = () => {
    repairService
      .getAllRepairs()
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
      repairService
        .deleteRepair(id)
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
    navigate(`/repairs/edit/${id}`);
  };

  const handleDetails = (vehicleId) => {
    navigate(`/repairs/details/vehicle/${vehicleId}`);
  };

  const handleApplyDiscount = (repair) => {
    if (repair.couponDiscount > 0) {
      setMessage("Ya se ha aplicado un descuento a esta reparación.");
      setOpen(true);
      return;
    }

    setSelectedRepair(repair);
    vehicleService.get(repair.vehicleId).then((vehicleResponse) => {
      const vehicleBrand = vehicleResponse.data.brand;

      discountService.getAll().then((response) => {
        const availableDiscounts = response.data.filter(discount => discount.brand.toLowerCase() === vehicleBrand.toLowerCase());
        if (availableDiscounts.length > 0) {
          const discount = availableDiscounts[0];
          discountService.getCouponQuantity(vehicleBrand).then((quantityResponse) => {
            const quantity = quantityResponse.data;
            if (quantity > 0) {
              setDiscounts(availableDiscounts);
              setMessage(`Para este vehículo de marca: ${vehicleBrand} hay ${quantity} cupones disponibles, ¿quieres aplicarlo?`);
            } else {
              setDiscounts([]);
              setMessage(`No hay descuentos disponibles para esta marca de vehículo.`);
            }
            setOpen(true);
          });
        } else {
          setDiscounts([]);
          setMessage("No hay descuentos disponibles para esta marca de vehículo.");
          setOpen(true);
        }
      });
    });
  };

  const handleConfirmApplyDiscount = () => {
    if (discounts.length > 0) {
      const brand = discounts[0].brand;
      console.log("Applying discount for brand:", brand);

      discountService.applyCoupon(selectedRepair.id, brand)
        .then(() => {
          console.log("Descuento aplicado.");
          init();
        })
        .catch((error) => {
          console.error("Error al aplicar el descuento:", error);
        });
    }
    setOpen(false);
  };

  const handleClose = () => {
    setOpen(false);
  };

  return (
    <TableContainer component={Paper}>
      <br />
      <Box display="flex" flexDirection="column" alignItems="center" mb={2}>
        <h2>Lista de Reparaciones</h2>
        <Link to="/repairs/create" style={{ textDecoration: "none" }}>
          <Button variant="contained" color="primary" startIcon={<AddIcon />}>
            Añadir Reparación
          </Button>
        </Link>
      </Box>
      <br />
      <Table sx={{ minWidth: 650 }} size="small" aria-label="a dense table">
        <TableHead>
          <TableRow>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>ID Reparación</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>ID Vehículo</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Fecha Entrada</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Hora Entrada</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Monto Reparación</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Monto Recargo</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Monto Descuento</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Monto Impuesto</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Costo Total</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Fecha Salida</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Hora Salida</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Fecha Recogida</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Hora Recogida</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Operaciones</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {repairs.map((repair) => (
            <TableRow key={repair.id}>
              <TableCell align="left">{repair.id}</TableCell>
              <TableCell align="left">{repair.vehicleId}</TableCell>
              <TableCell align="left">{repair.entryDate}</TableCell>
              <TableCell align="left">{repair.entryTime}</TableCell>
              <TableCell align="left">{repair.totalRepairAmount}</TableCell>
              <TableCell align="left">{repair.surchargeAmount}</TableCell>
              <TableCell align="left">{repair.discountAmount}</TableCell>
              <TableCell align="left">{repair.taxAmount}</TableCell>
              <TableCell align="left">{repair.totalCost}</TableCell>
              <TableCell align="left">{repair.exitDate}</TableCell>
              <TableCell align="left">{repair.exitTime}</TableCell>
              <TableCell align="left">{repair.pickUpDate}</TableCell>
              <TableCell align="left">{repair.pickUpTime}</TableCell>
              <TableCell>
                <Box display="flex" flexDirection="column" alignItems="center" justifyContent="center">
                  <Button
                    variant="contained"
                    color="info"
                    size="small"
                    onClick={() => handleDetails(repair.vehicleId)}
                    startIcon={<InfoIcon />}
                    sx={{ mb: 1, width: '100%' }}
                  >
                    Detalles
                  </Button>
                  <Button
                    variant="contained"
                    color="info"
                    size="small"
                    onClick={() => handleEdit(repair.id)}
                    startIcon={<EditIcon />}
                    sx={{ mb: 1, width: '100%' }}
                  >
                    Editar
                  </Button>
                  <Button
                    variant="contained"
                    color="info"
                    size="small"
                    onClick={() => handleApplyDiscount(repair)}
                    startIcon={<DiscountIcon />}
                    sx={{ mb: 1, width: '100%' }}
                  >
                    Top Car
                  </Button>
                  <Button
                    variant="contained"
                    color="error"
                    size="small"
                    onClick={() => handleDelete(repair.id)}
                    startIcon={<DeleteIcon />}
                    sx={{ mb: 1, width: '100%' }}
                  >
                    Eliminar
                  </Button>
                </Box>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>Aplicar Descuento</DialogTitle>
        <DialogContent>
          <DialogContentText>{message}</DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} color="primary">Cerrar</Button>
          {discounts.length > 0 && message.includes("¿quieres aplicarlo?") && (
            <Button onClick={handleConfirmApplyDiscount} color="primary">Aplicar</Button>
          )}
        </DialogActions>
      </Dialog>
    </TableContainer>
  );
};

export default RepairList;