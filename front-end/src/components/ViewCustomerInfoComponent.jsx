import React, { Component } from 'react'
import CustomerService from '../services/CustomerService'

class ViewCustomerInfoComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            customer: {
                name:'',
                surName:'',
                customerId:'',
                transactions:[]
            }
        }
    }
    
    viewAccount(id){
        // this.props.history.push(`/view-account/${id}`);
    }

    componentDidMount(){
        CustomerService.getCustomerInfo(this.state.id).then( res => {
            console.log(res.data);
            this.setState({customer: res.data});
        })
    }

    render() {
        return (
            <div>
                <br></br>
                <div className = "card col-md-6 offset-md-3">
                    <h3 className = "text-center"> View Customer Inforamtion</h3>
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
                <br></br>
                 <div className = "row">
                        <table className = "table table-striped table-bordered">
                            <thead>
                                <tr>
                                    <th> Transaction Type</th>
                                    <th> Transaction Amount</th>
                                    <th> Currency</th>
                                    <th> Account Id</th>
									<th> Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    this.state.customer.transactions.map(   
                                        transaction => 
                                        <tr key = {transaction.transactionId}>
                                             <td> {transaction.transactionType} </td>   
                                             <td> {transaction.amount}</td>
											 <td> {transaction.currency}</td>
											 <td> {transaction.accountId}</td>
                                             <td>
                                                <button style={{marginLeft: "10px"}} onClick={ () => this.viewAccount(transaction.accountId)} className="btn btn-info">view account </button>
                                             </td>
                                        </tr>
                                    )
                                }
                            </tbody>
                        </table>

                 </div>

            </div>
        )
    }
}

export default ViewCustomerInfoComponent
