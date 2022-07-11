import React, { Component } from 'react'
import CustomerService from '../services/CustomerService'

class ViewCustomerComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            customer: {}
        }
    }

    componentDidMount(){
        CustomerService.getCustomerById(this.state.id).then( res => {
            this.setState({customer: res.data});
        })
    }

    render() {
        return (
            <div>
                <br></br>
                <div className = "card col-md-6 offset-md-3">
                    <h3 className = "text-center"> View Customer Details</h3>
                    <div className = "card-body">
                        <div className = "row">
                            <label> <strong>Customer First Name:</strong></label>
                            <div> { this.state.customer.name }</div>
                        </div>
                        <div className = "row">
                            <label> <strong>Customer Last Name:</strong> </label>
                            <div> { this.state.customer.surName }</div>
                        </div>
                        <div className = "row">
                            <label> <strong>Customer Id:</strong> </label>
                            <div> { this.state.customer.customerId }</div>
                        </div>

                    </div>

                </div>
            </div>
        )
    }
}

export default ViewCustomerComponent
