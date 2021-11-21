import '../../styles/resource/ResourceForm.css';
import { useState, useEffect } from 'react';

const ResourceForm = () => {

    const [categories, setCategories] = useState(null);
    const [resource, setResource] = useState("");
    const [category, setCategory] = useState("-");


    useEffect(() => {
        fetch('http://localhost:8080/api/namespace')
            .then(response => response.json())
            .then(data => {
                setCategories(data);
            })
            .catch(err => console.log(err));


    }, [])

    const handleOnResourceChange = (e) => setResource(e.target.value);
    const handleOnCategoryChange = (e) => setCategory(e.target.value);

    const handleOnSubmit = (e) => {
        e.preventDefault();
        if (resource === "") {
            alert("Wprowadz czynnik");
            return;
        }
        if (category === "" || category === "-") {
            alert("Wprowadz kategorie");
            return;
        }
        const resorce = {
            name: resource,
            nameSpace: { name: category }
        };

        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(resorce)
        };
        fetch('http://localhost:8080/api/resource/add', requestOptions)
            .then(response => response.json())
            .then(data => {
                setCategory("");
                setResource("")
                //window.location.reload(false);
            })
            .catch(err => console.log(err));



    }

    return (
        <div>
            <h2>Formularz dodania czynnika</h2>
            <form>
                <label onSubmit="">
                    <input type="text" value={resource} onChange={handleOnResourceChange} placeholder="mieso/cynk..." required />
                    <select value={category} onChange={handleOnCategoryChange} required>
                        <option>-</option>
                        {categories != null ? categories.map(c => <option>{c.name}</option>) : undefined}
                    </select>
                    <button onClick={handleOnSubmit}>Dodaj</button>
                </label>

            </form>
        </div>

    );
}

export default ResourceForm;
