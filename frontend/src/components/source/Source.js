import '../../styles/source/Source.css';
import { useState } from 'react';

const Source = ({ id, name, properties }) => {

    const [isEdit, setIsEdit] = useState(false);
    const [newSource, setNewSource] = useState(name);

    const handleOnClick = (e) => {
        e.preventDefault();
        const requestOptions = {
            method: 'DELETE',
            headers: { 'Content-Type': 'application/json', "Access-Control-Allow-Origin": "*" }
        };
        fetch(`http://localhost:8080/api/resource/${id}`, requestOptions)
            .then(response => {
                if (response.status === 200) {
                    alert("Źródło Usunieto!");
                    return response.json();
                } else {
                    alert("Bład! Źródło jest w użyciu!")
                }

            })
            .then(data => {
                window.location.reload(false);
            })
            .catch(err => console.log(err));

    }

    const handleOnSourceChange = (e) => setNewSource(e.target.value);

    const handleOnEditSubmit = (e) => {
        e.preventDefault();

        if (newSource === "") {
            alert("Wprowadz źródło");
            return;
        }

        const Source2Update = {
            name: newSource
        };

        const requestOptions = {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(Source2Update)
        };
        fetch(`http://localhost:8080/api/resource/${id}`, requestOptions)
            .then(response => {
                if (response.status === 200) return response.json();

                if (response.status === 422) alert("Źródło juz istnieje!");
            })
            .then(data => {

                setNewSource("");
                window.location.reload(false);
            })
            .catch(err => console.log(err));



    }

    const handleOnEditClick = () => setIsEdit(p => !p);
    const editForm = (
        <form className="sourceEditForm" onSubmit="">
            <label >
                <input type="text" value={newSource} onChange={handleOnSourceChange} />

                <button onClick={handleOnEditSubmit}>Zatwierdź</button>
                <button onClick={handleOnEditClick}>Wróć</button>
            </label>

        </form>
    )

    const properties2Display = properties.map(property => <><span>{property.key} </span>{property.value} </>)
    const sourceItem = <li className="sourceItem">
        {` name: ${name} `}
        {properties2Display}
        <button onClick={handleOnEditClick}>Edytuj</button>
        <button onClick={handleOnClick}>Usun</button></li>

    return (
        <> {isEdit ? editForm : sourceItem}</>
    );
}

export default Source;
