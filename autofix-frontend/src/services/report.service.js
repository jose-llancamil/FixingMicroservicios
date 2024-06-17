import httpClient from "../http-common";

const getRepairTypeReport = (month, year) => {
    return httpClient.get('/reports/repair-type', {
        params: { month, year }
    });
};

const getMonthlyRepairComparisonReport = (month, year) => {
    return httpClient.get('/reports/monthly-comparison', {
        params: { month, year }
    });
};

export default {
    getRepairTypeReport,
    getMonthlyRepairComparisonReport
};