import httpClient from "../http-common";

const getAll = () => {
    return httpClient.get('/repair-list');
}

const create = data => {
    return httpClient.post("/repair-list", data);
}

const get = id => {
    return httpClient.get(`/repair-list/${id}`);
}

const update = (id, data) => {
    return httpClient.put(`/repair-list/${id}`, data);
}

const remove = id => {
    return httpClient.delete(`/repair-list/${id}`);
}

const getRepairPrice = (repairType, engineType) => {
    return httpClient.get('/repair-list/price', { 
        params: { 
            repairType, 
            engineType 
        }
    });
};

const getRepairTypes = () => {
    return httpClient.get('/repair-list/types');
  };

export default { getAll, create, get, update, remove, getRepairPrice, getRepairTypes };