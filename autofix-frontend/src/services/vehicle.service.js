import httpClient from "../http-common";

const getAll = () => {
    return httpClient.get('/vehicles');
}

const create = data => {
    return httpClient.post("/vehicles", data);
}

const get = id => {
    return httpClient.get(`/vehicles/${id}`);
}

const update = (id, data) => {
    return httpClient.put(`/vehicles/${id}`, data);
}

const remove = id => {
    return httpClient.delete(`/vehicles/${id}`);
}

export default { getAll, create, get, update, remove };