const API_BASE = "http://localhost:9191/api";

let idiomas = [];
let categorias = [];
let autores = [];
let editoriales = [];
let libros = [];

const mensaje = document.getElementById("mensaje");

document.addEventListener("DOMContentLoaded", function () {
    cargarDatos();

    document.getElementById("formIdioma").addEventListener("submit", guardarIdioma);
    document.getElementById("formCategoria").addEventListener("submit", guardarCategoria);
    document.getElementById("formAutor").addEventListener("submit", guardarAutor);
    document.getElementById("formEditorial").addEventListener("submit", guardarEditorial);
    document.getElementById("formLibro").addEventListener("submit", guardarLibro);

    document.getElementById("limpiarIdioma").addEventListener("click", limpiarIdioma);
    document.getElementById("limpiarCategoria").addEventListener("click", limpiarCategoria);
    document.getElementById("limpiarAutor").addEventListener("click", limpiarAutor);
    document.getElementById("limpiarEditorial").addEventListener("click", limpiarEditorial);
    document.getElementById("limpiarLibro").addEventListener("click", limpiarLibro);
});

async function cargarDatos() {
    await Promise.all([cargarIdiomas(), cargarCategorias(), cargarAutores(), cargarEditoriales()]);
    llenarCombosLibro();
    await cargarLibros();
}

async function cargarIdiomas() {
    idiomas = await consultar("/idiomas");
    pintarIdiomas();
}

async function cargarCategorias() {
    categorias = await consultar("/categorias");
    pintarCategorias();
}

async function cargarAutores() {
    autores = await consultar("/autores");
    pintarAutores();
}

async function cargarEditoriales() {
    editoriales = await consultar("/editoriales");
    pintarEditoriales();
}

async function cargarLibros() {
    libros = await consultar("/libros");
    pintarLibros();
}

async function consultar(ruta) {
    try {
        const respuesta = await fetch(API_BASE + ruta);
        if (!respuesta.ok) {
            throw new Error("Respuesta no valida");
        }
        return await respuesta.json();
    } catch (error) {
        mostrarMensaje("No se pudieron cargar los datos");
        return [];
    }
}

async function enviar(ruta, metodo, datos) {
    const respuesta = await fetch(API_BASE + ruta, {
        method: metodo,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(datos)
    });

    if (!respuesta.ok) {
        throw new Error("Operacion no valida");
    }
}

async function eliminar(ruta) {
    const respuesta = await fetch(API_BASE + ruta, { method: "DELETE" });
    if (!respuesta.ok && respuesta.status !== 204) {
        throw new Error("Operacion no valida");
    }
}

async function guardarIdioma(evento) {
    evento.preventDefault();
    const id = document.getElementById("idiomaId").value;
    const idioma = { nombre: document.getElementById("idiomaNombre").value };

    try {
        await enviar(id ? "/idiomas/" + id : "/idiomas", id ? "PUT" : "POST", idioma);
        limpiarIdioma();
        await cargarIdiomas();
        llenarCombosLibro();
        mostrarMensaje("Idioma guardado correctamente");
    } catch (error) {
        mostrarMensaje("Error al guardar idioma");
    }
}

async function guardarCategoria(evento) {
    evento.preventDefault();
    const id = document.getElementById("categoriaId").value;
    const categoria = { nombre: document.getElementById("categoriaNombre").value };

    try {
        await enviar(id ? "/categorias/" + id : "/categorias", id ? "PUT" : "POST", categoria);
        limpiarCategoria();
        await cargarCategorias();
        llenarCombosLibro();
        mostrarMensaje("Categoria guardada correctamente");
    } catch (error) {
        mostrarMensaje("Error al guardar categoria");
    }
}

async function guardarAutor(evento) {
    evento.preventDefault();
    const id = document.getElementById("autorId").value;
    const autor = {
        nombre: document.getElementById("autorNombre").value,
        idPais: numeroONull("autorPais"),
        email: valorONull("autorEmail"),
        website: valorONull("autorWebsite"),
        acercaDe: valorONull("autorAcerca")
    };

    try {
        await enviar(id ? "/autores/" + id : "/autores", id ? "PUT" : "POST", autor);
        limpiarAutor();
        await cargarAutores();
        llenarCombosLibro();
        mostrarMensaje("Autor guardado correctamente");
    } catch (error) {
        mostrarMensaje("Error al guardar autor");
    }
}

async function guardarEditorial(evento) {
    evento.preventDefault();
    const id = document.getElementById("editorialId").value;
    const editorial = {
        nombre: document.getElementById("editorialNombre").value,
        direccion: valorONull("editorialDireccion"),
        idPais: numeroONull("editorialPais"),
        email: valorONull("editorialEmail"),
        telefono: valorONull("editorialTelefono"),
        fax: valorONull("editorialFax"),
        website: valorONull("editorialWebsite"),
        cp: valorONull("editorialCp")
    };

    try {
        await enviar(id ? "/editoriales/" + id : "/editoriales", id ? "PUT" : "POST", editorial);
        limpiarEditorial();
        await cargarEditoriales();
        llenarCombosLibro();
        mostrarMensaje("Editorial guardada correctamente");
    } catch (error) {
        mostrarMensaje("Error al guardar editorial");
    }
}

async function guardarLibro(evento) {
    evento.preventDefault();
    const editando = document.getElementById("libroEditando").value;
    const isbn = document.getElementById("libroIsbn").value;
    const libro = {
        idIsbn: Number(isbn),
        titulo: document.getElementById("libroTitulo").value,
        autor: { idAutor: Number(document.getElementById("libroAutor").value) },
        editorial: { idEditorial: Number(document.getElementById("libroEditorial").value) },
        categoria: { idCategorias: Number(document.getElementById("libroCategoria").value) },
        idioma: { idIdiomas: Number(document.getElementById("libroIdioma").value) },
        stock: numeroONull("libroStock"),
        fechaPublicacion: valorONull("libroFecha")
    };

    try {
        await enviar(editando ? "/libros/" + editando : "/libros", editando ? "PUT" : "POST", libro);
        limpiarLibro();
        await cargarLibros();
        mostrarMensaje("Libro guardado correctamente");
    } catch (error) {
        mostrarMensaje("Error al guardar libro");
    }
}

async function eliminarIdioma(id) {
    await eliminarEntidad("/idiomas/" + id, cargarIdiomas, "Idioma eliminado", "No se puede eliminar el idioma");
    llenarCombosLibro();
}

async function eliminarCategoria(id) {
    await eliminarEntidad("/categorias/" + id, cargarCategorias, "Categoria eliminada", "No se puede eliminar la categoria");
    llenarCombosLibro();
}

async function eliminarAutor(id) {
    await eliminarEntidad("/autores/" + id, cargarAutores, "Autor eliminado", "No se puede eliminar el autor");
    llenarCombosLibro();
}

async function eliminarEditorial(id) {
    await eliminarEntidad("/editoriales/" + id, cargarEditoriales, "Editorial eliminada", "No se puede eliminar la editorial");
    llenarCombosLibro();
}

async function eliminarLibro(id) {
    await eliminarEntidad("/libros/" + id, cargarLibros, "Libro eliminado", "No se puede eliminar el libro");
}

async function eliminarEntidad(ruta, recargar, ok, error) {
    if (!confirm("Seguro que desea eliminar este registro?")) {
        return;
    }

    try {
        await eliminar(ruta);
        await recargar();
        mostrarMensaje(ok);
    } catch (e) {
        mostrarMensaje(error);
    }
}

function pintarIdiomas() {
    pintarSimple("tablaIdiomas", idiomas, "idIdiomas", editarIdioma, eliminarIdioma);
}

function pintarCategorias() {
    pintarSimple("tablaCategorias", categorias, "idCategorias", editarCategoria, eliminarCategoria);
}

function pintarSimple(tablaId, datos, idCampo, editar, borrar) {
    const tabla = document.getElementById(tablaId);
    tabla.innerHTML = "";
    datos.forEach(function (item) {
        const id = item[idCampo];
        const fila = document.createElement("tr");
        fila.innerHTML = `
            <td>${id}</td>
            <td>${texto(item.nombre)}</td>
            <td>${botonesAccion(id, editar.name, borrar.name)}</td>
        `;
        tabla.appendChild(fila);
    });
}

function pintarAutores() {
    const tabla = document.getElementById("tablaAutores");
    tabla.innerHTML = "";
    autores.forEach(function (autor) {
        const fila = document.createElement("tr");
        fila.innerHTML = `
            <td>${autor.idAutor}</td>
            <td>${texto(autor.nombre)}</td>
            <td>${texto(autor.email)}</td>
            <td>${botonesAccion(autor.idAutor, "editarAutor", "eliminarAutor")}</td>
        `;
        tabla.appendChild(fila);
    });
}

function pintarEditoriales() {
    const tabla = document.getElementById("tablaEditoriales");
    tabla.innerHTML = "";
    editoriales.forEach(function (editorial) {
        const fila = document.createElement("tr");
        fila.innerHTML = `
            <td>${editorial.idEditorial}</td>
            <td>${texto(editorial.nombre)}</td>
            <td>${texto(editorial.email)}</td>
            <td>${texto(editorial.telefono)}</td>
            <td>${botonesAccion(editorial.idEditorial, "editarEditorial", "eliminarEditorial")}</td>
        `;
        tabla.appendChild(fila);
    });
}

function pintarLibros() {
    const tabla = document.getElementById("tablaLibros");
    tabla.innerHTML = "";
    libros.forEach(function (libro) {
        const fila = document.createElement("tr");
        fila.innerHTML = `
            <td>${libro.idIsbn}</td>
            <td>${texto(libro.titulo)}</td>
            <td>${texto(libro.autor && libro.autor.nombre)}</td>
            <td>${texto(libro.editorial && libro.editorial.nombre)}</td>
            <td>${texto(libro.categoria && libro.categoria.nombre)}</td>
            <td>${texto(libro.idioma && libro.idioma.nombre)}</td>
            <td>${texto(libro.stock)}</td>
            <td>${botonesAccion(libro.idIsbn, "editarLibro", "eliminarLibro")}</td>
        `;
        tabla.appendChild(fila);
    });
}

function llenarCombosLibro() {
    llenarCombo("libroAutor", autores, "idAutor");
    llenarCombo("libroEditorial", editoriales, "idEditorial");
    llenarCombo("libroCategoria", categorias, "idCategorias");
    llenarCombo("libroIdioma", idiomas, "idIdiomas");
}

function llenarCombo(id, datos, idCampo) {
    const combo = document.getElementById(id);
    combo.innerHTML = "";
    datos.forEach(function (item) {
        const opcion = document.createElement("option");
        opcion.value = item[idCampo];
        opcion.textContent = item.nombre;
        combo.appendChild(opcion);
    });
}

function editarIdioma(id) {
    const idioma = idiomas.find((item) => item.idIdiomas === id);
    if (!idioma) return;
    document.getElementById("idiomaId").value = idioma.idIdiomas;
    document.getElementById("idiomaNombre").value = idioma.nombre;
}

function editarCategoria(id) {
    const categoria = categorias.find((item) => item.idCategorias === id);
    if (!categoria) return;
    document.getElementById("categoriaId").value = categoria.idCategorias;
    document.getElementById("categoriaNombre").value = categoria.nombre;
}

function editarAutor(id) {
    const autor = autores.find((item) => item.idAutor === id);
    if (!autor) return;
    document.getElementById("autorId").value = autor.idAutor;
    document.getElementById("autorNombre").value = autor.nombre;
    document.getElementById("autorPais").value = autor.idPais || "";
    document.getElementById("autorEmail").value = autor.email || "";
    document.getElementById("autorWebsite").value = autor.website || "";
    document.getElementById("autorAcerca").value = autor.acercaDe || "";
}

function editarEditorial(id) {
    const editorial = editoriales.find((item) => item.idEditorial === id);
    if (!editorial) return;
    document.getElementById("editorialId").value = editorial.idEditorial;
    document.getElementById("editorialNombre").value = editorial.nombre;
    document.getElementById("editorialDireccion").value = editorial.direccion || "";
    document.getElementById("editorialPais").value = editorial.idPais || "";
    document.getElementById("editorialEmail").value = editorial.email || "";
    document.getElementById("editorialTelefono").value = editorial.telefono || "";
    document.getElementById("editorialFax").value = editorial.fax || "";
    document.getElementById("editorialWebsite").value = editorial.website || "";
    document.getElementById("editorialCp").value = editorial.cp || "";
}

function editarLibro(id) {
    const libro = libros.find((item) => item.idIsbn === id);
    if (!libro) return;
    document.getElementById("libroEditando").value = libro.idIsbn;
    document.getElementById("libroIsbn").value = libro.idIsbn;
    document.getElementById("libroTitulo").value = libro.titulo;
    document.getElementById("libroAutor").value = libro.autor ? libro.autor.idAutor : "";
    document.getElementById("libroEditorial").value = libro.editorial ? libro.editorial.idEditorial : "";
    document.getElementById("libroCategoria").value = libro.categoria ? libro.categoria.idCategorias : "";
    document.getElementById("libroIdioma").value = libro.idioma ? libro.idioma.idIdiomas : "";
    document.getElementById("libroStock").value = libro.stock || "";
    document.getElementById("libroFecha").value = libro.fechaPublicacion || "";
}

function limpiarIdioma() {
    document.getElementById("formIdioma").reset();
    document.getElementById("idiomaId").value = "";
}

function limpiarCategoria() {
    document.getElementById("formCategoria").reset();
    document.getElementById("categoriaId").value = "";
}

function limpiarAutor() {
    document.getElementById("formAutor").reset();
    document.getElementById("autorId").value = "";
}

function limpiarEditorial() {
    document.getElementById("formEditorial").reset();
    document.getElementById("editorialId").value = "";
}

function limpiarLibro() {
    document.getElementById("formLibro").reset();
    document.getElementById("libroEditando").value = "";
}

function botonesAccion(id, editar, borrar) {
    return `
        <button onclick="${editar}(${id})">Editar</button>
        <button onclick="${borrar}(${id})">Eliminar</button>
    `;
}

function valorONull(id) {
    const valor = document.getElementById(id).value.trim();
    return valor === "" ? null : valor;
}

function numeroONull(id) {
    const valor = document.getElementById(id).value;
    return valor === "" ? null : Number(valor);
}

function texto(valor) {
    return valor === null || valor === undefined ? "" : valor;
}

function mostrarMensaje(textoMensaje) {
    mensaje.textContent = textoMensaje;
}
