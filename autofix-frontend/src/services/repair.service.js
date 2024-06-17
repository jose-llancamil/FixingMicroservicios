import httpClient from "../http-common";

// Repair Methods
const getAllRepairs = () => {
    return httpClient.get('/repairs');
}

const getRepairById = (id) => {
    return httpClient.get(`/repairs/${id}`);
}

const createRepair = (data) => {
    return httpClient.post('/repairs', data);
}

const updateRepair = (id, data) => {
    return httpClient.put(`/repairs/${id}`, data);
}

const deleteRepair = (id) => {
    return httpClient.delete(`/repairs/${id}`);
}

// Repair Details Methods
const getAllRepairDetails = () => {
    return httpClient.get('/repairs/details');
}

const getRepairDetailsById = (id) => {
    return httpClient.get(`/repairs/details/${id}`);
}

const getRepairDetailsByVehicleId = (vehicleId) => {
    return httpClient.get(`/repairs/details/vehicle/${vehicleId}`);
}

const createRepairDetails = (data) => {
    return httpClient.post('/repairs/details', data);
}

const updateRepairDetails = (id, data) => {
    return httpClient.put(`/repairs/details/${id}`, data);
}

const deleteRepairDetailById = (id) => {
    return httpClient.delete(`/repairs/details/${id}`);
}

const deleteRepairDetailsByVehicleId = (vehicleId) => {
    return httpClient.delete(`/repairs/details/vehicle/${vehicleId}`);
}

// Vehicle Repair History
const getVehicleRepairHistory = () => {
    return httpClient.get('/repairs/history');
}

export default {
    getAllRepairs,
    getRepairById,
    createRepair,
    updateRepair,
    deleteRepair,
    getAllRepairDetails,
    getRepairDetailsById,
    getRepairDetailsByVehicleId,
    createRepairDetails,
    updateRepairDetails,
    deleteRepairDetailById,
    deleteRepairDetailsByVehicleId,
    getVehicleRepairHistory
};