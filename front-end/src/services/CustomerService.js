import axios from 'axios';

const CUSTOMER_API_BASE_URL = "http://localhost:8091/v1/customers";

const CUSTOMER_INFO_API_BASE_URL = "http://localhost:8091/v1/customers/customer-info";

const ACCOUNT_API_BASE_URL = "http://localhost:8090/v1/accounts";

class CustomerService {

    getCustomers(){
        return axios.get(CUSTOMER_API_BASE_URL);
    }

    createCustomer(customer){
        return axios.post(CUSTOMER_API_BASE_URL, customer);
    }

    openAccount(customer){
        return axios.post(ACCOUNT_API_BASE_URL,customer);
    }

    getCustomerInfo(customerId){
        return axios.get(CUSTOMER_INFO_API_BASE_URL + '/' + customerId);
    }

    getCustomerById(customerId){
        return axios.get(CUSTOMER_API_BASE_URL + '/' + customerId);
    }

    updateCustomer(customer, customerId){
        return axios.put(CUSTOMER_API_BASE_URL + '/' + customerId, customer);
    }

    deleteCustomer(customerId){
        return axios.delete(CUSTOMER_API_BASE_URL + '/' + customerId);
    }
}

export default new CustomerService()