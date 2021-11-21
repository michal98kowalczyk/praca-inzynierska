import '../../styles/statement/StatementItem.css';



const StatementItem = ({ statement }) => {
    const properties = statement.properties.map(property => <><span>{property.key} </span>{property.value} </>)

    const handleOnClick = (e) => {
        e.preventDefault();
        console.log(statement);
        const requestOptions = {
            method: 'DELETE',
            headers: { 'Content-Type': 'application/json', "Access-Control-Allow-Origin": "*" }
        };
        fetch(`http://localhost:8080/api/statement/${statement.id}`, requestOptions)
            .then(response => {
                if (response.status == 200) return response.json();

            })
            .then(data => {
                alert("Statement Usunieto!");
                window.location.reload(false);
            })
            .catch(err => console.log(err));

    }

    return (
        <li>
            <span>{statement.subject.name} </span>
            <span>{statement.predicate.verb} </span>


            {statement.isRes && <span>{statement.resource.name} </span>}
            {statement.isLit && <span>{statement.literal.value} </span>}
            {statement.probablity}<br />
            {properties}
            <button onClick={handleOnClick}>Usun</button>
        </li >
    );
}

export default StatementItem;
