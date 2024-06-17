const Home = () => {
  return (
    <div>
      <h1>AutoFix Management System</h1>
      <p>
        AutoFix es una aplicación web diseñada para la gestión de reparaciones de vehículos en talleres especializados. Esta plataforma facilita el registro de vehículos, el seguimiento de reparaciones y la generación de reportes relacionados con las actividades del taller.
      </p>
      <p>
        La aplicación ha sido desarrollada utilizando una arquitectura de microservicios, aprovechando tecnologías modernas como:
      </p>
      <ul>
        <li><a href="https://spring.io/projects/spring-boot">Spring Boot</a> para el backend,</li>
        <li><a href="https://reactjs.org/">React</a> para el frontend, y</li>
        <li><a href="https://www.postgresql.org/">PostgreSQL</a> para la base de datos.</li>
      </ul>
      <p>
        AutoFix está desplegada utilizando <a href="https://minikube.sigs.k8s.io/docs/">Minikube</a>, proporcionando una solución escalable y eficiente para la gestión de reparaciones de vehículos.
      </p>
      <p>
        El backend de la aplicación consta de los siguientes componentes:
      </p>
      <ul>
        <li><strong>Config Server:</strong> Servidor de configuración centralizado.</li>
        <li><strong>Eureka Server:</strong> Servicio de registro y descubrimiento.</li>
        <li><strong>Gateway Server:</strong> Pasarela API para el enrutamiento de solicitudes.</li>
        <li><strong>MS Repair List:</strong> Microservicio para la gestión de listas de reparaciones.</li>
        <li><strong>MS Repairs:</strong> Microservicio para la gestión de reparaciones individuales.</li>
        <li><strong>MS Reports:</strong> Microservicio para la generación de reportes.</li>
        <li><strong>MS Vehicles:</strong> Microservicio para la gestión de vehículos.</li>
      </ul>
      <p>
        El sistema proporciona una interfaz intuitiva y un enfoque modular para gestionar eficientemente las necesidades del taller y mejorar la satisfacción del cliente.
      </p>
    </div>
  );
};

export default Home;