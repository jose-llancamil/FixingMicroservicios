import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from "./components/Navbar";
import Home from './components/Home';
import VehicleList from './components/VehicleList';
import AddEditVehicle from './components/AddEditVehicle';
import RepairPricesList from './components/RepairPricesList';
import AddEditRepairPricesList from './components/AddEditRepairPricesList';
import RepairList from './components/RepairList';
import AddEditRepair from './components/AddEditRepair';
import RepairDetails from './components/RepairDetails';
import AddEditRepairDetails from './components/AddEditRepairDetails';
import RepairHistory from './components/RepairHistory';
import RepairTypeReport from './components/RepairTypeReport';
import MonthlyRepairComparisonReport from './components/MonthlyRepairComparisonReport'; // Importa el nuevo componente
import NotFound from './components/NotFound';

function App() {
  return (
    <Router>
      <div className="container">
        <Navbar />
        
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/vehicles" element={<VehicleList />} />
          <Route path="/vehicles/create" element={<AddEditVehicle />} />
          <Route path="/vehicles/edit/:id" element={<AddEditVehicle />} />
          <Route path="/repair-list" element={<RepairPricesList />} />
          <Route path="/repair-list/create" element={<AddEditRepairPricesList />} />
          <Route path="/repair-list/edit/:id" element={<AddEditRepairPricesList />} />
          <Route path="/repairs" element={<RepairList />} />
          <Route path="/repairs/create" element={<AddEditRepair />} />
          <Route path="/repairs/edit/:id" element={<AddEditRepair />} />
          <Route path="/repairs/details/vehicle/:vehicleId" element={<RepairDetails />} />
          <Route path="/repair-details/create/:vehicleId" element={<AddEditRepairDetails />} />
          <Route path="/repair-details/edit/:id/:vehicleId" element={<AddEditRepairDetails />} />
          <Route path="/repairs/history" element={<RepairHistory />} />
          <Route path="/reports/repair-type" element={<RepairTypeReport />} />
          <Route path="/reports/monthly-comparison" element={<MonthlyRepairComparisonReport />} />
          <Route path="*" element={<NotFound />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;