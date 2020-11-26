import './App.css';
import React, {useEffect, useState} from 'react';
import { Table } from 'reactstrap';

const TransferRequestTable = ({data}) => {
  const [ items, setItems] = useState([]);
  const [ msg, setMsg ] = useState();

  const generateTableBody = (items) => {
    return (
      items.map((item) => (
        <tr key={item.id}>
          <td>{item.id}</td>
          <td>{item.txUid}</td>
          <td>{item.debitAcct}</td>
          <td>{item.debitIC}</td>
          <td>{item.creditAcct}</td>
          <td>{item.creditIC}</td>
          <td>{item.amount}</td>
          <td>{item.status}</td>
          <td>{item.reason}</td>
        </tr>
      ))
    );
  };
  
  useEffect(() => {
    setItems(data);
  }, [data]);

  useEffect(() => {
    setMsg(generateTableBody(items));
  }, [items]);
  

  return (
    <div>
      <Table>
        <thead>
          <tr>
            <th>#</th>
            <th>Tx Uid</th>
            <th>From user (Account)</th>
            <th>From user (IC Number)</th>
            <th>To user (Account)</th>
            <th>To user (IC Number)</th>
            <th>Amount</th>
            <th>Status</th>
            <th>Reason</th>
          </tr>
        </thead>
        <tbody>
          {msg}
        </tbody>
      </Table>   
    </div>
  );
}

export default TransferRequestTable;
