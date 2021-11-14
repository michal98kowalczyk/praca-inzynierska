import '../styles/ModelForm.css';
import { useState } from 'react';

const ModelForm = ({ }) => {
    const [modelName, setModelName] = useState();

    const handleOnChange = (e) => {
        setModelName(e.target.value);
    }

    const handleOnClick = (e) => {
        e.preventDefault();

        if (modelName === "") {
            alert("Wprowadz nazwe modelu");
            return;
        }

        const model = { name: modelName };

        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(model)
        };
        fetch('http://localhost:8080/api/model/add', requestOptions)
            .then(response => response.json())
            .then(data => console.log(data))
            .catch(err => console.log(err));



    }

    return (
        <form>
            <label htmlFor="m_name">Nazwa modelu </label>
            <input id="m_name" type="text" placeholder="Wprowadź nazwe" value={modelName} onChange={handleOnChange} />

            <button onClick={handleOnClick}>Dodaj</button>

        </form>
    );
}

export default ModelForm;
