import '../../styles/category/Category.css';

const Category = ({ id, name }) => {

    return (
        <li>{` name: ${name}`}</li>
    );
}

export default Category;
