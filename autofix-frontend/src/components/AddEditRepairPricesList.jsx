import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import repairPricesListService from "../services/repairPricesList.service";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import FormControl from "@mui/material/FormControl";
import SaveIcon from "@mui/icons-material/Save";

const AddEditRepairPricesList = () => {
  const [repairType, setRepairType] = useState("");
  const [gasolinePrice, setGasolinePrice] = useState("");
  const [dieselPrice, setDieselPrice] = useState("");
  const [hybridPrice, setHybridPrice] = useState("");
  const [electricPrice, setElectricPrice] = useState("");
  const { id } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    if (id) {
      repairPricesListService.get(id)
        .then(response => {
          const repair = response.data;
          setRepairType(repair.repairType);
          setGasolinePrice(repair.gasolinePrice);
          setDieselPrice(repair.dieselPrice);
          setHybridPrice(repair.hybridPrice);
          setElectricPrice(repair.electricPrice);
        })
        .catch(error => {
          console.log("Error fetching repair details.", error);
        });
    }
  }, [id]);

  const saveRepair = (e) => {
    e.preventDefault();
    const repair = {
      repairType,
      gasolinePrice,
      dieselPrice,
      hybridPrice,
      electricPrice
    };

    if (id) {
      repairPricesListService.update(id, repair)
        .then(response => {
          console.log("Repair price has been updated.", response.data);
          navigate("/repair-list");
        })
        .catch(error => {
          console.log("Error updating repair price.", error);
        });
    } else {
      repairPricesListService.create(repair)
        .then(response => {
          console.log("Repair price has been added.", response.data);
          navigate("/repair-list");
        })
        .catch(error => {
          console.log("Error adding repair price.", error);
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
      onSubmit={saveRepair}
    >
      <h3>{id ? "Editar Tipo Reparación" : "Nuevo Tipo Reparación"}</h3>
      <hr />
      <FormControl fullWidth>
        <TextField label="Tipo de Reparación" value={repairType} onChange={e => setRepairType(e.target.value)} sx={{ mb: 1 }} />
      </FormControl>
      <FormControl fullWidth>
        <TextField label="Precio Gasolina" type="number" value={gasolinePrice} onChange={e => setGasolinePrice(e.target.value)} sx={{ mb: 1 }} />
      </FormControl>
      <FormControl fullWidth>
        <TextField label="Precio Diésel" type="number" value={dieselPrice} onChange={e => setDieselPrice(e.target.value)} sx={{ mb: 1 }} />
      </FormControl>
      <FormControl fullWidth>
        <TextField label="Precio Híbrido" type="number" value={hybridPrice} onChange={e => setHybridPrice(e.target.value)} sx={{ mb: 1 }} />
      </FormControl>
      <FormControl fullWidth>
        <TextField label="Precio Eléctrico" type="number" value={electricPrice} onChange={e => setElectricPrice(e.target.value)} sx={{ mb: 1 }} />
      </FormControl>
      <Button variant="contained" color="primary" type="submit" startIcon={<SaveIcon />}>
        Guardar
      </Button>
      <hr />
    </Box>
  );
};

export default AddEditRepairPricesList;