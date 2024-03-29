import '../../styles/verb/Verb.css';
import { useState } from 'react';

const Verb = ({ id, verb }) => {

    const [isEdit, setIsEdit] = useState(false);
    const [newVerb, setNewVerb] = useState(verb);

    const handleOnClick = (e) => {
        e.preventDefault();
        const requestOptions = {
            method: 'DELETE',
            headers: { 'Content-Type': 'application/json', "Access-Control-Allow-Origin": "*" }
        };
        fetch(`http://localhost:8080/api/verb/${id}`, requestOptions)
            .then(response => {
                if (response.status === 200) {
                    alert("Relacje Usunieto!");
                    return response.json();
                } else {
                    alert("Bład! Relacja jest w użyciu!")
                }

            })
            .then(data => {
                window.location.reload(false);
            })
            .catch(err => console.log(err));

    }

    const handleOnVerbChange = (e) => setNewVerb(e.target.value);

    const handleOnEditSubmit = (e) => {
        e.preventDefault();
        if (newVerb === "") {
            alert("Wprowadz relacje");
            return;
        }

        const verb = {
            verb: newVerb
        };

        const requestOptions = {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(verb)
        };
        fetch(`http://localhost:8080/api/verb/${id}`, requestOptions)
            .then(response => {
                if (response.status === 200) return response.json();

                if (response.status === 422) alert("Relacja juz istnieje!");
            })
            .then(data => {

                setNewVerb("");
                window.location.reload(false);
            })
            .catch(err => console.log(err));



    }

    const handleOnEditClick = () => setIsEdit(p => !p);
    const editForm = (
        <form className="verbForm">
            <label onSubmit={handleOnEditSubmit}>
                <input type="text" value={newVerb} onChange={handleOnVerbChange} />

                <button onClick={handleOnEditSubmit}>Zatwierdź</button>
                <button onClick={handleOnEditClick}>Wróć</button>
            </label>

        </form>
    )


    const verbItem = <li className="verbItem">{` verb: ${verb}`} <button onClick={handleOnEditClick}>Edytuj</button> <button onClick={handleOnClick}>Usun</button></li>


    return (
        <> {isEdit ? editForm : verbItem}</>
    );
}

export default Verb;
