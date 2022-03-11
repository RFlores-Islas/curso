// Call the dataTables jQuery plugin
$(document).ready(function() {
    cargarUsuario();
  $('#usuarios').DataTable();
  actualizarEmailUsuario();
});

function actualizarEmailUsuario() {
    document.getElementById('txt-email-usuario').outerHTML = localStorage.email;
}

async function cargarUsuario() {
  const request = await fetch('usuarios', {
    method: 'GET',
    headers: getHeaders()
  });
  const usuarios = await request.json();
  let listadoHtml = '';
  for (let usuario of usuarios) {
     let textTelefono = usuario.telefono == null ? '-' : usuario.telefono;
     let buttonDelete = '<a href="#" onclick="eliminarUsuario(' + usuario.id + ')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';
     let usuarioHtml ='<tr><td>' + usuario.id + '</td><td>' + usuario.nombre + ' ' + usuario.apellido +'</td><td>' + usuario.email + '</td><td>' + textTelefono + '</td><td>' + buttonDelete + '</td></tr>';

     listadoHtml += usuarioHtml;
  }

  console.log(usuarios);
  document.querySelector('#usuarios tbody').outerHTML = listadoHtml;//PASA LOS PARAMETROS OBTENIDOS A LA TABLA #usuarios
 }
 //async da la funcion de espera a la palabra reservada await

function getHeaders() {
    return {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Authorization': localStorage.token
    };
}


 async function eliminarUsuario(id) {

 if (!confirm('¿Desea eliminar este usuario?')) {
    return;
 }

 const request = await fetch('usuarios/' + id, {
    method: 'DELETE',
    headers: getHeaders()
  });
  location.reload()
 }