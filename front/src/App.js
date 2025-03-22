import React from 'react';
import SearchForm from './components/SearchForm';

function App() {
  const handleSearch = (searchData) => {
    console.log("Search form data:", searchData);
    alert("Search successful! Redirecting to available cars would happen here.");
  };

  return (
    <div className="App">
      <main>
        <SearchForm onSearch={handleSearch} />
      </main>
    </div>
  );
}

export default App;