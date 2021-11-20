import '../../styles/category/CategoryForm.css';
import { useState, useEffect } from 'react';
import CategoriesList from './CategoriesList';

const CategoryForm = () => {
    const [categories, setCategories] = useState(null);
    const [newCategory, setNewCategory] = useState("");



    useEffect(() => {
        fetch('http://localhost:8080/api/namespace')
            .then(response => response.json())
            .then(data => {
                setCategories(data);
            })
            .catch(err => console.log(err));


    }, [])

    const handleOnSubmit = (e) => {
        e.preventDefault();

        if (newCategory === "") {
            alert("Wprowadz kategorie");
            return;
        }

        const category = { name: newCategory };

        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(category)
        };
        fetch('http://localhost:8080/api/namespace/add', requestOptions)
            .then(response => response.json())
            .then(data => {
                setNewCategory("");
                window.location.reload(false);
            })
            .catch(err => console.log(err));
    }

    const handleOnCategoryChange = (e) => setNewCategory(e.target.value);


    return (
        <div>
            <form onSubmit={handleOnSubmit}>
                <h2>Formularz dodania kategorii</h2>
                <label htmlFor="">
                    <input type="text" value={newCategory} onChange={handleOnCategoryChange} placeholder="produkt/choroba..." />
                </label>
                <button>Dodaj</button>

            </form>

            <section className="availableCategories">
                <h2>Dostepne kategorie</h2>

                {categories != null ? <CategoriesList categories={categories} /> : undefined}

            </section>
        </div>
    );
}

export default CategoryForm;
