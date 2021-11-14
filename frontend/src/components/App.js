import Header from './Header'
import Main from './Main'
import ModelDetails from './ModelDetails'
import ModelForm from './ModelForm'
import StatementForm from './StatementForm'
import PageNotFound from './PageNotFound'
import { Routes, Route } from "react-router-dom";

import '../styles/App.css';


const App = () => {

  return (
    <div className="App">
      <Header />

      <Routes>

        <Route path='/' element={<Main />} />

        <Route path="/model/:id/:name" element={<ModelDetails />} />
        <Route path="/model/form" element={<ModelForm />} />
        <Route path="/statement/form" element={<StatementForm />} />
        <Route path='*' element={<PageNotFound />} />

      </Routes>
    </div>
  );
}

export default App;
