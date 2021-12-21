import '../../styles/source/SourceForm.css';
import { useState, useEffect } from 'react';

const AUTOR = 'Autor';
const Title = 'Tytuł';
const Publisher = 'Wydawca';
const Year = 'Rok';

const ArticleForm = ({ propertyChange, properties, source, onSourceChange }) => {


    return (
        <>
            <label>
                Nazwa
            </label>
            <input type="text" value={source} onChange={onSourceChange} placeholder="Nazwa" required />

            <label>
                Autor
            </label>
            <input type="text" value={properties[AUTOR]} onChange={(e) => propertyChange(AUTOR, e.target.value)} placeholder="Autor" required />

            <label>
                Tytuł
            </label>
            <input type="text" value={properties[Title]} onChange={(e) => propertyChange(Title, e.target.value)} placeholder="Tytuł" required />

            <label>
                Wydawca
            </label>
            <input type="text" value={properties[Publisher]} onChange={(e) => propertyChange(Publisher, e.target.value)} placeholder="Wydawca" />


            <label>
                Rok
            </label>
            <input type="number" value={properties[Year]} step="1" min="1899" max="2099" onChange={(e) => propertyChange(Year, e.target.value)} placeholder="Rok" />





        </>
    );
}

export default ArticleForm;