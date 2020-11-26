import './App.css';
import React, { Component } from 'react';
import { Button } from 'reactstrap';
import TransferRequestTable from './TransferRequestTable';
import CreateNewTransferRequest from './CreateNewTransferRequest';


class App extends Component {

  constructor(props){
    super(props);
    
    this.state = {
      items: [],
      ic: '',
      txuid: ''
    };
    
    this.icChange = this.icChange.bind(this);
    this.txUidChange = this.txUidChange.bind(this);
    this.searchByIc = this.searchByIc.bind(this);
    this.checkStatusByTxUid = this.checkStatusByTxUid.bind(this);
  }

  componentDidUpdate(prevousState){
    //console.log(this.state.items);
  }

  icChange(event){
    this.setState({ic: event.target.value});
  }
  
  txUidChange(event){
    this.setState({txuid: event.target.value});
  }

  searchByIc(){
    fetch('http://localhost:8081/listAllTransferRequestByIcNo/' + this.state.ic)
    .then(res => res.json())
    .then((result) => this.setState({items: result}));
  }

  checkStatusByTxUid(){
    fetch('http://localhost:8081/checkTransferStatusForTXUID/' + this.state.txuid)
    .then(res => res.text())
    .then((result) => alert( this.state.txuid + " : " + result));
  }

  render() {
    return <div>
        <div className="App container">
          <h1>Simple Tranfer Service</h1>
          <div>
            <form>
              <label className="mr-2">
              Search All Transfer Request By IC No.: <input type="text" value={this.state.ic} onChange={this.icChange} />
              </label>
              <Button color="primary" onClick={this.searchByIc}>Search</Button>
            </form>
            <form>
              <label className="mr-2">
              Check Request Status By Tx Uid: <input type="text" value={this.state.txuid} onChange={this.txUidChange} />
              </label>
              <Button color="primary" onClick={this.checkStatusByTxUid}>Check</Button>
            </form>
            <CreateNewTransferRequest/>
            <br/>
          </div>
          <TransferRequestTable data={this.state.items}/>
        </div>
      </div>;
  }
}

export default App;
