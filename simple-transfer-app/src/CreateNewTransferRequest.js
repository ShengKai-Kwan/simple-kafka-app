import React, {useState} from 'react';
import { ModalBody, ModalFooter, ModalHeader, Button, Modal, Label, FormGroup, Input } from 'reactstrap';

const CreateNewTransferRequest = () => {

    const [newBookModal, toggle] = useState(false);
    const [ transferData, setTransferData]= useState({
        debitAcct : '',
        debitIC : '',
        creditAcct : '',
        creditIC : '',
        amount : ''
    });

    const toggleNewRequestModel = () => {
        toggle(!newBookModal);
    };

    const createTransfer = () => {
        console.log(transferData);
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(transferData)
        };
        fetch('http://localhost:8081/transfer', requestOptions)
            .then(response => response.json())
            .then(data => alert("Created Transfer Request with TxUid: " + data.txUid));
        toggleNewRequestModel();
    }

    return (
        <div>
            <Button color="danger" onClick={toggleNewRequestModel}>Add New Transfer Request</Button>
            <Modal isOpen={newBookModal} toggle={toggleNewRequestModel}>
                <ModalHeader toggle={toggleNewRequestModel}>New Transfer Request</ModalHeader>
                <ModalBody>
                    <FormGroup>
                        <Label for="debitAcct">From User (Account)</Label>
                        <Input id="debitAcct" onChange={(e) => {
                            let tempData = transferData;
                            tempData.debitAcct = e.target.value;
                            setTransferData(tempData);
                        }}/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="debitIC">From User (IC No.)</Label>
                        <Input id="debitIC" onChange={(e) => {
                            let tempData = transferData;
                            tempData.debitIC = e.target.value;
                            setTransferData(tempData);
                        }}/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="creditAcct">To User (Account)</Label>
                        <Input id="creditAcct" onChange={(e) => {
                            let tempData = transferData;
                            tempData.creditAcct = e.target.value;
                            setTransferData(tempData);
                        }}/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="creditIC">To User (IC No.)</Label>
                        <Input id="creditIC" onChange={(e) => {
                            let tempData = transferData;
                            tempData.creditIC = e.target.value;
                            setTransferData(tempData);
                        }}/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="amount">Amount</Label>
                        <Input id="amount" onChange={(e) => {
                            let tempData = transferData;
                            tempData.amount = e.target.value;
                            setTransferData(tempData);
                        }}/>
                    </FormGroup>
                </ModalBody>
                <ModalFooter>
                    <Button type="submit" color="primary" onClick={createTransfer}>Transfer</Button>
                    <Button color="secondary" onClick={toggleNewRequestModel}>Cancel</Button>
                </ModalFooter>
            </Modal>
        </div>
    );

}

export default CreateNewTransferRequest;