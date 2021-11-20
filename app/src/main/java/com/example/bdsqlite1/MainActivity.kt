package com.example.bdsqlite1

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var et_Codigo:EditText
    private lateinit var et_Descripcion:EditText
    private lateinit var et_Precio:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        et_Codigo = findViewById(R.id.et_Codigo)
        et_Descripcion = findViewById(R.id.et_Descripcion)
        et_Precio = findViewById(R.id.et_Precio)

    }

    //Este metodo realizar el registro de articulos o productos
    fun Registrar(Vista:View){
        val admin = AdminSQLite(this,"Tienda",null,1)
        val BD:SQLiteDatabase = admin.writableDatabase //se crea la conexion a la base de datos

        val codigo = et_Codigo.text.toString()
        val descripcion = et_Descripcion.text.toString()
        val precio = et_Precio.text.toString()

        if(!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()) {
            val registro = ContentValues().apply {
            put("codigo", codigo)
            put("descripcion", descripcion)
            put("precio", precio)
        }
            BD.insert("articulos",null,registro)
            BD.close()//cerramos la base de datos
            et_Codigo.setText("")
            et_Descripcion.setText("")
            et_Precio.setText("")
            Toast.makeText(this,"Registro exitoso",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this,"Debes llenar todos los campos",Toast.LENGTH_SHORT).show()
        }

    }

    fun Buscar(Vista:View){
        val admin = AdminSQLite(this,"Tienda",null,1)
        val BD:SQLiteDatabase = admin.writableDatabase //se crea la conexion a la base de datos

        val codigo = et_Codigo.text.toString()
        if(!codigo.isEmpty()){
            val file = BD.rawQuery("select descripcion, precio from articulos where codigo=$codigo",null)
            if(file.moveToFirst()){
                et_Descripcion.setText(file.getString(0))
                et_Precio.setText(file.getString(1))
                BD.close()
            }else{
                Toast.makeText(this,"No existe el articulo",Toast.LENGTH_LONG).show()
                BD.close()
            }
        }else{
            Toast.makeText(this,"Debes ingresar un codigo del articulo",Toast.LENGTH_LONG).show()
        }

    }

    fun Modificar(Vista: View) {
        val admin = AdminSQLite(this, "Tienda", null, 1)
        val BD: SQLiteDatabase = admin.writableDatabase //se crea la conexion a la base de datos

        val codigo = et_Codigo.text.toString()
        val descripcion = et_Descripcion.text.toString()
        val precio = et_Precio.text.toString()

        if (!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()) {
            val registro = ContentValues().apply {
                put("codigo", codigo)
                put("descripcion", descripcion)
                put("precio", precio)
            }
            val cantidad: Int = BD.update("articulos", registro, "codigo=$codigo", null)
            BD.close()

            if (cantidad == 1) {
                Toast.makeText(this, "Articulo modificado correctamente", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "No existe el articulo ", Toast.LENGTH_LONG).show()
                BD.close()
            }
            et_Codigo.setText("")
            et_Descripcion.setText("")
            et_Precio.setText("")

            Toast.makeText(this, "Registro exitose ", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this,"Debe llenar todos los campos ",Toast.LENGTH_LONG).show()
        }
    }
    fun Eliminar(Vista: View){
        val admin = AdminSQLite(this, "Tienda", null, 1)
        val BD: SQLiteDatabase = admin.writableDatabase //se crea la conexion a la base de d

        val codigo = et_Codigo.text.toString()
        if(!codigo.isEmpty()){
            val cantidad:Int = BD.delete("articulos","codigo=$codigo",null)
            BD.close()
            et_Codigo.setText("")
            et_Descripcion.setText("")
            et_Precio.setText("")

            if(cantidad==1){
                Toast.makeText(this, "Articulo eliminado exitosamente ", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this, "No existe el articulo ", Toast.LENGTH_LONG).show()
                BD.close()
            }
        }else{
            Toast.makeText(this, "Debe insertar un codigo del articulo ", Toast.LENGTH_LONG).show()
        }
    }
}