import '../../styles/model/ModelForm.css';
import { useState } from 'react';

const ModelForm = () => {
    const [modelName, setModelName] = useState("");

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
            .then(response => {
                if (response.status === 200) {
                    setModelName("");
                    window.location.reload(false);
                    return response.json();
                }
                else if (response.status === 422) alert("Model juz istnieje");
            })
            .catch(err => console.log(err));



    }

    return (
        <form className="model">
            <h2>Formularz dodania modelu </h2>
            <label htmlFor="m_name"></label>
            <input id="m_name" type="text" placeholder="Wprowadź nazwe" value={modelName} onChange={handleOnChange} />

            <button onClick={handleOnClick}>Dodaj</button>

        </form>
    );
}

export default ModelForm;
