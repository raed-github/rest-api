import React, { Component } from 'react'
import CustomerService from '../services/CustomerService';

class CreateCustomerComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            // step 2
            id: this.props.match.params.id,
            name: '',
            surName: '',
            balance: 0.0
        }
        this.changeNameHandler = this.changeNameHandler.bind(this);
        this.changeSurNameHandler = this.changeSurNameHandler.bind(this);
        this.openAccount = this.openAccount.bind(this);
    }

    // step 3
    componentDidMount(){

        // step 4
        if(this.state.id === '_add'){
            return
        }else{
            CustomerService.getCustomerById(this.state.id).then( (res) =>{
                let customer = res.data;
                this.setState({name: customer.name,
                    surName: customer.surName,
                    customerId : customer.customerId,
                    balance:0.0
                });
            });
        }        
    }
    openAccount = (e) => {
        e.preventDefault();
        let customer = {customerId:this.state.id, name: this.state.name, surName: this.state.surName, balance: this.state.balance};
        console.log('customer => ' + JSON.stringify(customer));
        CustomerService.openAccount(customer).then(res =>{
            this.props.history.push('/customers');
        });
    }
    
    changeNameHandler= (event) => {
        this.setState({name: event.target.value});
    }

    changeSurNameHandler= (event) => {
        this.setState({surName: event.target.value});
    }

    changeBalanceHandler= (event) => {
        console.log("balance=");
        this.setState({balance: event.target.value});

        console.log(event.target.value);
    }

    cancel(){
        this.props.history.push('/customers');
    }

    getTitle(){
        if(this.state.id === '_add'){
            return <h3 className="text-center">Open Account</h3>
        }else{
            return <h3 className="text-center">Update Customer</h3>
        }
    }
    render() {
        return (
            <div>
                <br></br>
                   <div className = "container">
                        <div className = "row">
                            <div className = "card col-md-6 offset-md-3 offset-md-3">
                                {
                                    this.getTitle()
                                }
                                <div className = "card-body">
                                    <form>
                                        <div className = "form-group">
                                            <label> Customer First Name: </label>
                                            { this.state.name }
                                        </div>

                                        <div className = "form-group">
                                            <label> Customer Last Name: </label>
                                            { this.state.surName }  
                                        </div>
                                        <div className = "form-group">
                                            <label> Amount: </label>
                                            <input placeholder="Account Balance" name="balance" className="form-control" 
                                                value={this.state.balance} onChange={this.changeBalanceHandler}/>
                                        </div>

                                        <button className="btn btn-success" onClick={this.openAccount}>Open Account</button>
                                        <button className="btn btn-danger" onClick={this.cancel.bind(this)} style={{marginLeft: "10px"}}>Cancel</button>
                                    </form>
                                </div>
                            </div>
                        </div>

                   </div>
            </div>
        )
    }
}

export default CreateCustomerComponent