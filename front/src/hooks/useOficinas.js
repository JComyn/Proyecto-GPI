import { useState, useEffect } from "react";
import { getOficinas } from "services/oficinasService";

export const useOficinas = () => {
  const [oficinas, setOficinas] = useState([]);
  const [errorOficinas, setErrorOficinas] = useState(null);

  useEffect(() => {
    const fetchOficinas = async () => {
      try {
        const data = await getOficinas();
        setOficinas(data);
      } catch (err) {
        setErrorOficinas(err);
      }
    };

    fetchOficinas();
  }, []);

  return { oficinas, errorOficinas };
};