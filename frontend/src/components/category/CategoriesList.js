import '../../styles/category/CategoriesList.css';
import Category from './Category';

const CategoriesList = (props) => {

    const categories = props.categories.map(c => {
        return <Category key={c.id} id={c.id} name={c.name} />
    });
    return (

        < ul > {categories}</ul >
    );
}

export default CategoriesList;
