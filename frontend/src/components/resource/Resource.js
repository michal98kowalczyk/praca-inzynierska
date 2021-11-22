import '../../styles/resource/Resource.css';
import { useState } from 'react';

const Resource = ({ id, name, category, categories }) => {

    const [isEdit, setIsEdit] = useState(false);
    const [newResource, setNewResource] = useState(name);

    const handleOnClick = (e) => {
        e.preventDefault();
        const requestOptions = {
            method: 'DELETE',
            headers: { 'Content-Type': 'application/json', "Access-Control-Allow-Origin": "*" }
        };
        fetch(`http://localhost:8080/api/resource/${id}`, requestOptions)
            .then(response => {
                if (response.status === 200) {
                    alert("Czynnik Usunieto!");
                    return response.json();
                } else {
                    alert("Bład! Czynnik jest w użyciu!")
                }

            })
            .then(data => {
                window.location.reload(false);
            })
            .catch(err => console.log(err));

    }

    const handleOnResourceChange = (e) => setNewResource(e.target.value);

    const handleOnEditSubmit = (e) => {
        e.preventDefault();

        if (newResource === "") {
            alert("Wprowadz czynnik");
            return;
        }

        const resource2Update = {
            name: newResource
        };

        const requestOptions = {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(resource2Update)
        };
        fetch(`http://localhost:8080/api/resource/${id}`, requestOptions)
            .then(response => {
                if (response.status === 200) return response.json();

                if (response.status === 422) alert("Czynnik juz istnieje!");
            })
            .then(data => {

                setNewResource("");
                window.location.reload(false);
            })
            .catch(err => console.log(err));



    }

    const handleOnEditClick = () => setIsEdit(p => !p);
    const editForm = (
        <form className="resourceForm" onSubmit="">
            <label >
                <input type="text" value={newResource} onChange={handleOnResourceChange} />
                <input type="text" value={category.name} disabled />

                <button onClick={handleOnEditSubmit}>Zatwierdź</button>
                <button onClick={handleOnEditClick}>Wróć</button>
            </label>

        </form>
    )


    const resourceItem = <li className="resourceItem">{` name: ${name} category: ${category.name}`} <button onClick={handleOnEditClick}>Edytuj</button> <button onClick={handleOnClick}>Usun</button></li>

    return (
        <> {isEdit ? editForm : resourceItem}</>
    );
}

export default Resource;
