
package com.emergentes.controller;

import com.emergentes.m.Nota;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "MainServlet", urlPatterns = {"/MainServlet"})
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       String opcion=request.getParameter("op");
        Nota objno=new Nota();
        int id,pos;
        HttpSession ses=request.getSession();
        List<Nota> lista= (List<Nota>) ses.getAttribute("lista");
        switch(opcion){
            case "nuevo":
              
                request.setAttribute("miobjnot", objno);
                request.getRequestDispatcher("editar.jsp").forward(request, response);
                break;
            case "editar":
                id=Integer.parseInt(request.getParameter("id"));
              
                pos=buscarPorIndice(request, id);
                
                objno=lista.get(pos);
               
                request.setAttribute("miobjnot", objno);
                request.getRequestDispatcher("editar.jsp").forward(request, response);
                break;
            case "eliminar":
               
                id=Integer.parseInt(request.getParameter("id"));
                pos=buscarPorIndice(request, id);
                if(pos >= 0){
                    lista.remove(pos);
                }
               
                request.setAttribute("lista", lista);
                response.sendRedirect("index.jsp");
            break;
                
            default:
                request.setAttribute("lista", lista);
                response.sendRedirect("index.jsp");
        }
        
    }

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       int id=Integer.parseInt(request.getParameter("id"));
        HttpSession ses=request.getSession();
        ArrayList<Nota> lista=(ArrayList<Nota>) ses.getAttribute("lista");
        Nota objno=new Nota();
        objno.setId(id);
        objno.setHora(request.getParameter("hora"));
        objno.setActividad(request.getParameter("actividad"));
        objno.setCompletado(request.getParameter("completado"));
        System.out.println("El ID es "+ id);
        if(id==0){
           
            int idNuevo=obtenerId(request);
            objno.setId(idNuevo);
            lista.add(objno);
            System.out.println(objno.toString());
        }else{
            int pos=buscarPorIndice(request, id);
            lista.set(pos, objno);
            System.out.println(objno.toString());
        }
        System.out.println("Enviando as index");
        request.setAttribute("lista", lista);
        response.sendRedirect("index.jsp");
    }
    
public int buscarPorIndice(HttpServletRequest request, int id){
    HttpSession ses=request.getSession();
    List<Nota> lista=(List<Nota>) ses.getAttribute("lista");
    int pos =-1;
    if(lista !=null){
        for(Nota ele: lista){
            ++pos;
            if(ele.getId()==id){
                break;
            }
        }
    }
    return pos;
}
public int obtenerId(HttpServletRequest request){
    HttpSession ses= request.getSession();
    ArrayList<Nota> lista = (ArrayList<Nota>) ses.getAttribute("lista");
    int idn=0;
    for(Nota ele:lista){
        idn=ele.getId();
    }
return idn +1;
}

}
