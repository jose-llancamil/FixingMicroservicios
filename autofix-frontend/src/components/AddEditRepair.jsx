import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import repairService from "../services/repair.service";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import FormControl from "@mui/material/FormControl";
import SaveIcon from "@mui/icons-material/Save";
import { LocalizationProvider, DatePicker, TimePicker } from "@mui/x-date-pickers";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import dayjs from "dayjs";

const AddEditRepair = () => {
  const [vehicleId, setVehicleId] = useState("");
  const [entryDate, setEntryDate] = useState(null);
  const [entryTime, setEntryTime] = useState(null);
  const [totalRepairAmount, setTotalRepairAmount] = useState(0);
  const [surchargeAmount, setSurchargeAmount] = useState(0);
  const [discountAmount, setDiscountAmount] = useState(0);
  const [taxAmount, setTaxAmount] = useState(0);
  const [totalCost, setTotalCost] = useState(0);
  const [exitDate, setExitDate] = useState(null);
  const [exitTime, setExitTime] = useState(null);
  const [pickUpDate, setPickUpDate] = useState(null);
  const [pickUpTime, setPickUpTime] = useState(null);
  const { id } = useParams();
  const navigate = useNavigate();
  const isEdit = Boolean(id);

  useEffect(() => {
    if (id) {
      repairService.getRepairById(id)
        .then(response => {
          const repair = response.data;
          setVehicleId(repair.vehicleId || "");
          setEntryDate(repair.entryDate ? dayjs(repair.entryDate) : null);
          setEntryTime(repair.entryTime ? dayjs(repair.entryTime, "HH:mm:ss") : null);
          setTotalRepairAmount(repair.totalRepairAmount || 0);
          setSurchargeAmount(repair.surchargeAmount || 0);
          setDiscountAmount(repair.discountAmount || 0);
          setTaxAmount(repair.taxAmount || 0);
          setTotalCost(repair.totalCost || 0);
          setExitDate(repair.exitDate ? dayjs(repair.exitDate) : null);
          setExitTime(repair.exitTime ? dayjs(repair.exitTime, "HH:mm:ss") : null);
          setPickUpDate(repair.pickUpDate ? dayjs(repair.pickUpDate) : null);
          setPickUpTime(repair.pickUpTime ? dayjs(repair.pickUpTime, "HH:mm:ss") : null);
        })
        .catch(error => {
          console.log("Error fetching repair details.", error);
        });
    }
  }, [id]);

  const saveRepair = (e) => {
    e.preventDefault();
    const formattedEntryDate = entryDate ? entryDate.format("YYYY-MM-DD") : null;
    const formattedEntryTime = entryTime ? entryTime.format("HH:mm:ss") : null;
    const formattedExitDate = exitDate ? exitDate.format("YYYY-MM-DD") : null;
    const formattedExitTime = exitTime ? exitTime.format("HH:mm:ss") : null;
    const formattedPickUpDate = pickUpDate ? pickUpDate.format("YYYY-MM-DD") : null;
    const formattedPickUpTime = pickUpTime ? pickUpTime.format("HH:mm:ss") : null;

    const repair = {
      vehicleId,
      entryDate: formattedEntryDate,
      entryTime: formattedEntryTime,
      totalRepairAmount,
      surchargeAmount,
      discountAmount,
      taxAmount,
      totalCost,
      exitDate: formattedExitDate,
      exitTime: formattedExitTime,
      pickUpDate: formattedPickUpDate,
      pickUpTime: formattedPickUpTime
    };

    if (id) {
        repairService.updateRepair(id, repair)
          .then(response => {
            console.log("Repair has been updated.", response.data);
            navigate("/repairs");
          })
          .catch(error => {
            console.log("Error updating repair.", error);
          });
      } else {
        repairService.createRepair(repair)
          .then(response => {
            console.log("Repair has been added.", response.data);
            navigate("/repairs");
          })
          .catch(error => {
            console.log("Error adding vehicle.", error);
          });
      }
    };

  return (
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <Box
        display="flex"
        flexDirection="column"
        alignItems="center"
        justifyContent="center"
        component="form"
        onSubmit={saveRepair}
        sx={{ mt: 4, width: '100%', maxWidth: 600 }}
      >
        <h3>{id ? "Editar Reparación" : "Agregar Reparación"}</h3>
        <hr />
        <FormControl fullWidth sx={{ mb: 2 }}>
          <TextField
            label="ID del Vehículo"
            type="number"
            value={vehicleId}
            onChange={e => setVehicleId(e.target.value)}
            sx={{ mb: 1 }}
          />
        </FormControl>
        <DatePicker
          label="Fecha de Entrada"
          value={entryDate}
          onChange={setEntryDate}
          slotProps={{ textField: { fullWidth: true, sx: { mb: 2 } } }}
        />
        <TimePicker
          label="Hora de Entrada"
          value={entryTime}
          onChange={setEntryTime}
          slotProps={{ textField: { fullWidth: true, sx: { mb: 2 } } }}
        />
        <DatePicker
          label="Fecha de Salida"
          value={exitDate}
          onChange={setExitDate}
          slotProps={{ textField: { fullWidth: true, sx: { mb: 2 } } }}
        />
        <TimePicker
          label="Hora de Salida"
          value={exitTime}
          onChange={setExitTime}
          slotProps={{ textField: { fullWidth: true, sx: { mb: 2 } } }}
        />
        <DatePicker
          label="Fecha de Recogida"
          value={pickUpDate}
          onChange={setPickUpDate}
          slotProps={{ textField: { fullWidth: true, sx: { mb: 2 } } }}
        />
        <TimePicker
          label="Hora de Recogida"
          value={pickUpTime}
          onChange={setPickUpTime}
          slotProps={{ textField: { fullWidth: true, sx: { mb: 2 } } }}
        />
        <FormControl fullWidth sx={{ mb: 2 }}>
          <TextField
            label="Monto Reparación"
            type="number"
            value={totalRepairAmount}
            onChange={e => setTotalRepairAmount(Number(e.target.value))}
            sx={{ mb: 1 }}
            InputProps={{ readOnly: !isEdit }}
          />
        </FormControl>
        <FormControl fullWidth sx={{ mb: 2 }}>
          <TextField
            label="Monto Recargo"
            type="number"
            value={surchargeAmount}
            onChange={e => setSurchargeAmount(Number(e.target.value))}
            sx={{ mb: 1 }}
            InputProps={{ readOnly: !isEdit }}
          />
        </FormControl>
        <FormControl fullWidth sx={{ mb: 2 }}>
          <TextField
            label="Monto Descuento"
            type="number"
            value={discountAmount}
            onChange={e => setDiscountAmount(Number(e.target.value))}
            sx={{ mb: 1 }}
            InputProps={{ readOnly: !isEdit }}
          />
        </FormControl>
        <FormControl fullWidth sx={{ mb: 2 }}>
          <TextField
            label="Monto Impuesto"
            type="number"
            value={taxAmount}
            onChange={e => setTaxAmount(Number(e.target.value))}
            sx={{ mb: 1 }}
            InputProps={{ readOnly: !isEdit }}
          />
        </FormControl>
        <FormControl fullWidth sx={{ mb: 2 }}>
          <TextField
            label="Costo Total"
            type="number"
            value={totalCost}
            onChange={e => setTotalCost(Number(e.target.value))}
            sx={{ mb: 1 }}
            InputProps={{ readOnly: !isEdit }}
          />
        </FormControl>
        <Button variant="contained" color="primary" type="submit" startIcon={<SaveIcon />} sx={{ mt: 2 }}>
          Guardar
        </Button>
        <hr />
      </Box>
    </LocalizationProvider>
  );
};

export default AddEditRepair;