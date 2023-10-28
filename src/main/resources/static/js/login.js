function login() {
  // Cria uma lista com duas opções
  const options = [
    {
      label: "Cliente",
      href: "/login",
    },
    {
      label: "Supervisor",
      href: "/admin",
    },
  ];

  // Exibe a lista
  const element = document.querySelector(".nav-pills");
  element.innerHTML = options.map((option) => (
    <li class="nav-item">
      <a class="nav-linki" href={option.href} onClick={(e) => login(e.currentTarget)}>{option.label}</a>
    </li>
  ));
}