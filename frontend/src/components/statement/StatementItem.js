import '../../styles/statement/StatementItem.css';
import { useState } from 'react';
import StatementItemDetails from './StatementItemDetails';



const StatementItem = ({ statement }) => {
    const [isEdit, setIsEdit] = useState(false);
    const [newConfidence, setnewConfidence] = useState(statement.confidence);
    const [isDetails, setIsDetails] = useState(false);
    const [isStats, setIsStats] = useState(false);

    const properties = statement.properties.map(property => <><span>{property.key} </span>{property.value} </>)
    const handleOnClick = (e) => {
        e.preventDefault();
        const requestOptions = {
            method: 'DELETE',
            headers: { 'Content-Type': 'application/json', "Access-Control-Allow-Origin": "*" }
        };
        fetch(`http://localhost:8080/api/statement/${statement.id}`, requestOptions)
            .then(response => {
                if (response.status === 200) return response.json();

            })
            .then(data => {
                alert("Statement Usunieto!");
                window.location.reload(false);
            })
            .catch(err => console.log(err));

    }
    //----------------------------


    const handleOnEditSubmit = (e) => {
        e.preventDefault();
        if (newConfidence < -1 || newConfidence > 1) {
            alert("Błędne confidence");
            return;
        }

        const statement2Update = {

            confidence: newConfidence
        }


        const requestOptions = {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(statement2Update)
        };

        fetch(`http://localhost:8080/api/statement/${statement.id}`, requestOptions)
            .then(response => {
                if (response.status === 200) return response.json();

                if (response.status === 422) alert("Statement juz istnieje!");
            })
            .then(data => {

                setnewConfidence(statement.confidence);
                window.location.reload(false);
            })
            .catch(err => console.log(err));



    }
    const showStats = () => {
        setIsStats((prev) => !prev);
    }

    const showStatementDetails = () => {
        setIsDetails((prev) => !prev);
    }

    const handleOnnewConfidenceChange = (e) => setnewConfidence(e.target.value);

    const handleOnEditClick = () => setIsEdit(p => !p);
    const editForm = (
        <form className="statementEditForm">


            <label htmlFor="subject">Czynnik
                <input id="subject" type="text" value={statement.subject.name} disabled />

            </label>

            <label htmlFor="predicate">zależność

                <input id="predicate" type="text" value={statement.predicate.verb} disabled />
            </label>
            {statement.isRes &&
                <label htmlFor="resource">obiekt
                    <input id="resource" type="text" value={statement.resource.name} disabled />

                </label>}

            {statement.isLit &&
                <label htmlFor="literal">literał
                    <input id="literal" type="text" value={statement.literal.value} disabled />
                </label>

            }

            <label htmlFor="confidence">wiarygodność
            </label>
            <input id="confidence" type="number" step="0.01" min="-1" max="1" value={newConfidence} onChange={handleOnnewConfidenceChange} />

            <button onClick={handleOnEditSubmit}>Zatwierdź</button>
            <button onClick={handleOnEditClick}>Wróć</button>
        </form>

    )


    const statementItem = <li className="statementItem">
        <span>{statement.subject.name} </span>
        <span>{statement.predicate.verb} </span>


        {statement.isRes && <span>{statement.resource.name} </span>}
        {statement.isLit && <span>{statement.literal.value} </span>}
        {statement.confidence}<br />
        {properties}
        <button onClick={handleOnEditClick}>Edytuj</button>
        <button onClick={handleOnClick}>Usun</button>
        <button onClick={showStatementDetails}>Szczegóły</button>
    </li >


    return (
        <>
            {isEdit ? editForm : statementItem}
            {isDetails ? <StatementItemDetails statement={statement} closeDetails={setIsDetails} /> : undefined}
        </>
    );
}

export default StatementItem;
