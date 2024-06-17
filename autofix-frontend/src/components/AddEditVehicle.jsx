import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import vehicleService from "../services/vehicle.service";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import FormControl from "@mui/material/FormControl";
import SaveIcon from "@mui/icons-material/Save";

const AddEditVehicle = () => {
  const [licensePlate, setlicensePlate] = useState("");
  const [brand, setBrand] = useState("");
  const [model, setModel] = useState("");
  const [type, setType] = useState("");
  const [yearOfManufacture, setyearOfManufacture] = useState("");
  const [engineType, setEngineType] = useState("");
  const [numberOfSeats, setnumberOfSeats] = useState("");
  const [mileage, setMileage] = useState("");
  const { id } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    if (id) {
      vehicleService.get(id)
        .then(response => {
          const vehicle = response.data;
          setlicensePlate(vehicle.licensePlate);
          setBrand(vehicle.brand);
          setModel(vehicle.model);
          setType(vehicle.type);
          setyearOfManufacture(vehicle.yearOfManufacture);
          setEngineType(vehicle.engineType);
          setnumberOfSeats(vehicle.numberOfSeats);
          setMileage(vehicle.mileage);
        })
        .catch(error => {
          console.log("Error fetching vehicle details.", error);
        });
    }
  }, [id]);

  const saveVehicle = (e) => {
    e.preventDefault();
    const vehicle = {
      licensePlate,
      brand,
      model,
      type,
      yearOfManufacture,
      engineType,
      numberOfSeats,
      mileage
    };

    if (id) {
      vehicleService.update(id, vehicle)
        .then(response => {
          console.log("Vehicle has been updated.", response.data);
          navigate("/vehicles");
        })
        .catch(error => {
          console.log("Error updating vehicle.", error);
        });
    } else {
      vehicleService.create(vehicle)
        .then(response => {
          console.log("Vehicle has been added.", response.data);
          navigate("/vehicles");
        })
        .catch(error => {
          console.log("Error adding vehicle.", error);
        });
    }
  };

  return (
    <Box
      display="flex"
      flexDirection="column"
      alignItems="center"
      justifyContent="center"
      component="form"
      onSubmit={saveVehicle}
    >
      <h3>{id ? "Editar Vehículo" : "Nuevo Vehículo"}</h3>
      <hr />
      <FormControl fullWidth>
        <TextField label="Patente" value={licensePlate} onChange={e => setlicensePlate(e.target.value)} sx={{ mb: 1 }} />
      </FormControl>
      <FormControl fullWidth>
        <TextField label="Marca" value={brand} onChange={e => setBrand(e.target.value)} sx={{ mb: 1 }} />
      </FormControl>
      <FormControl fullWidth>
        <TextField label="Modelo" value={model} onChange={e => setModel(e.target.value)} sx={{ mb: 1 }} />
      </FormControl>
      <FormControl fullWidth>
        <TextField label="Tipo" value={type} onChange={e => setType(e.target.value)} sx={{ mb: 1 }} />
      </FormControl>
      <FormControl fullWidth>
        <TextField label="Año" value={yearOfManufacture} onChange={e => setyearOfManufacture(e.target.value)} sx={{ mb: 1 }} />
      </FormControl>
      <FormControl fullWidth>
        <TextField label="Tipo de Motor" value={engineType} onChange={e => setEngineType(e.target.value)} sx={{ mb: 1 }} />
      </FormControl>
      <FormControl fullWidth>
        <TextField label="Asientos" type="number" value={numberOfSeats} onChange={e => setnumberOfSeats(e.target.value)} sx={{ mb: 1 }} />
      </FormControl>
      <FormControl fullWidth>
        <TextField label="Kilometraje" type="number" value={mileage} onChange={e => setMileage(e.target.value)} sx={{ mb: 1 }} />
      </FormControl>
      <Button variant="contained" color="primary" type="submit" startIcon={<SaveIcon />}>
        Guardar
      </Button>
      <hr />
    </Box>
  );
};

export default AddEditVehicle;