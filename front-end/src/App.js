import React from 'react';
import logo from './logo.svg';
import './App.css';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import ListCustomerComponent from './components/ListCustomerComponent';
import HeaderComponent from './components/HeaderComponent';
import FooterComponent from './components/FooterComponent';
import CreateCustomerComponent from './components/CreatCustomer';
import OpenAccountComponent from './components/OpenAccountComponent';
import ViewCustomerComponent from './components/ViewCustomerComponent';

function App() {
  return (
    <div>
        <Router>
              <HeaderComponent />
                <div className="container">
                    <Switch> 
                          <Route path = "/" exact component = {ListCustomerComponent}></Route>
                          <Route path = "/customers" component = {ListCustomerComponent}></Route>
                          <Route path = "/add-customer/:id" component = {CreateCustomerComponent}></Route>
                          <Route path = "/view-customer/:id" component = {ViewCustomerComponent}></Route>
                          <Route path = "/open-account/:id" component = {OpenAccountComponent}></Route> 
                    </Switch>
                </div>
              <FooterComponent />
        </Router>
    </div>
    
  );
}

export default App;
