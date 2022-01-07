import '../../styles/source/SourceForm.css';


const AUTOR = 'Autor';
const Title = 'Tytuł';
const URL = 'Url';
const Year = 'Rok';

const OtherSourceForm = ({ onSourceChange, source, properties, propertyChange }) => {



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
                Url
            </label>
            <input type="text" value={properties[URL]} onChange={(e) => propertyChange(URL, e.target.value)} placeholder="Url" required />


            <label>
                Rok
            </label>
            <input type="number" value={properties[Year]} step="1" min="1899" max="2099" onChange={(e) => propertyChange(Year, e.target.value)} placeholder="Rok" />



        </>

    );
}

export default OtherSourceForm;
