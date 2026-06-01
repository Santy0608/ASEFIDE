create or replace PACKAGE BODY ASEFIDE_PKG AS

    -- IMPLEMENTACIÓN PARA FIDE_USUARIO_TB
    
    PROCEDURE FIDE_USUARIOS_INSERTAR_SP(
        P_IDENTIFICACION        IN FIDE_USUARIO_TB.IDENTIFICACION%TYPE,
        P_NOMBRE                IN FIDE_USUARIO_TB.NOMBRE%TYPE,
        P_APELLIDO_PATERNO      IN FIDE_USUARIO_TB.APELLIDO_PATERNO%TYPE,
        P_APELLIDO_MATERNO      IN FIDE_USUARIO_TB.APELLIDO_MATERNO%TYPE,
        P_ID_DATOS_ASOCIADOS    IN FIDE_USUARIO_TB.ID_DATOS_ASOCIADOS%TYPE,
        P_ID_ESTADO             IN FIDE_USUARIO_TB.ID_ESTADO%TYPE,
        P_ID_DIRECCION          IN FIDE_USUARIO_TB.ID_DIRECCION%TYPE,
        P_NOMBRE_USUARIO        IN FIDE_USUARIO_TB.NOMBRE_USUARIO%TYPE,
        P_CONTRASENIA           IN FIDE_USUARIO_TB.CONTRASENIA%TYPE,
        P_CORREOS_IDS  IN SYS.ODCINUMBERLIST,
        P_NUMEROS_IDS  IN SYS.ODCINUMBERLIST,
        P_ID_USUARIO            OUT NUMBER
    ) IS
        V_NOMBRE_USUARIO_EXISTE NUMBER;
        V_IDENTIFICACION_EXISTE NUMBER;
    BEGIN
    
        -- Valida que el nombre de usuario no esté duplicado
        SELECT COUNT(*) INTO V_NOMBRE_USUARIO_EXISTE
        FROM FIDE_USUARIO_TB
        WHERE UPPER(NOMBRE_USUARIO) = UPPER(P_NOMBRE_USUARIO);
    
        IF V_NOMBRE_USUARIO_EXISTE > 0 THEN
            RAISE_APPLICATION_ERROR(-20001,
                'El nombre de usuario ' || P_NOMBRE_USUARIO || ' ya está en uso');
        END IF;
        
        -- Valida que la identificacion no esté duplicada --
        SELECT COUNT(*) INTO V_IDENTIFICACION_EXISTE
        FROM FIDE_USUARIO_TB
        WHERE IDENTIFICACION = P_IDENTIFICACION;
    
        IF V_IDENTIFICACION_EXISTE > 0 THEN
            RAISE_APPLICATION_ERROR(-20002,
                'Ya existe un usuario con la identificación: ' || P_IDENTIFICACION);
        END IF;
    
        -- 1. Insertar usuario SIN correo
        INSERT INTO FIDE_USUARIO_TB(
            IDENTIFICACION, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO,
            ID_DATOS_ASOCIADOS, ID_ESTADO, ID_DIRECCION,
            NOMBRE_USUARIO, CONTRASENIA
        ) VALUES (
            P_IDENTIFICACION, P_NOMBRE, P_APELLIDO_PATERNO, P_APELLIDO_MATERNO,
            P_ID_DATOS_ASOCIADOS, P_ID_ESTADO, P_ID_DIRECCION,
            P_NOMBRE_USUARIO, P_CONTRASENIA
        ) RETURNING ID_USUARIO INTO P_ID_USUARIO;
    
        FOR i IN 1..P_CORREOS_IDS.COUNT LOOP
            INSERT INTO FIDE_USUARIOS_CORREOS_TB(USUARIO_ID, CORREO_ID)
            VALUES (P_ID_USUARIO, P_CORREOS_IDS(i));
        END LOOP;
    
        IF P_NUMEROS_IDS IS NOT NULL THEN
            FOR j IN 1..P_NUMEROS_IDS.COUNT LOOP 
                INSERT INTO FIDE_USUARIOS_TELEFONOS_TB(USUARIO_ID, NUMERO_ID)
                VALUES (P_ID_USUARIO, P_NUMEROS_IDS(j));
            END LOOP;
        END IF;
    
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            RAISE_APPLICATION_ERROR(-20001, 'Usuario ya existe');
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20002, SQLERRM);
    END;

    PROCEDURE FIDE_USUARIOS_EDITAR_SP(
        P_ID_USUARIO         IN FIDE_USUARIO_TB.ID_USUARIO%TYPE,
        P_IDENTIFICACION     IN FIDE_USUARIO_TB.IDENTIFICACION%TYPE,
        P_NOMBRE             IN FIDE_USUARIO_TB.NOMBRE%TYPE,
        P_APELLIDO_PATERNO   IN FIDE_USUARIO_TB.APELLIDO_PATERNO%TYPE,
        P_APELLIDO_MATERNO   IN FIDE_USUARIO_TB.APELLIDO_MATERNO%TYPE,
        P_ID_DATOS_ASOCIADOS IN FIDE_USUARIO_TB.ID_DATOS_ASOCIADOS%TYPE,
        P_ID_ESTADO          IN FIDE_USUARIO_TB.ID_ESTADO%TYPE,
        P_ID_DIRECCION       IN FIDE_USUARIO_TB.ID_DIRECCION%TYPE,
        P_NOMBRE_USUARIO     IN FIDE_USUARIO_TB.NOMBRE_USUARIO%TYPE,
        P_CONTRASENIA        IN FIDE_USUARIO_TB.CONTRASENIA%TYPE,
        P_CORREOS_IDS        IN SYS.ODCINUMBERLIST,
        P_NUMEROS_IDS        IN SYS.ODCINUMBERLIST
    ) IS
        V_NOMBRE_USUARIO_EXISTE NUMBER;
        V_IDENTIFICACION_EXISTE NUMBER;
    BEGIN
        
        -- Valida que el nombre de usuario no esté duplicado
        SELECT COUNT(*) INTO V_NOMBRE_USUARIO_EXISTE
        FROM FIDE_USUARIO_TB
        WHERE UPPER(NOMBRE_USUARIO) = UPPER(P_NOMBRE_USUARIO);
    
        IF V_NOMBRE_USUARIO_EXISTE > 0 THEN
            RAISE_APPLICATION_ERROR(-20001,
                'El nombre de usuario ' || P_NOMBRE_USUARIO || ' ya está en uso');
        END IF;
        
        -- Valida que la identificacion no esté duplicada --
        SELECT COUNT(*) INTO V_IDENTIFICACION_EXISTE
        FROM FIDE_USUARIO_TB
        WHERE IDENTIFICACION = P_IDENTIFICACION;
    
        IF V_IDENTIFICACION_EXISTE > 0 THEN
            RAISE_APPLICATION_ERROR(-20002,
                'Ya existe un usuario con la identificación: ' || P_IDENTIFICACION);
        END IF;
    
        UPDATE FIDE_USUARIO_TB
        SET IDENTIFICACION = P_IDENTIFICACION,
            NOMBRE = P_NOMBRE,
            APELLIDO_PATERNO = P_APELLIDO_PATERNO,
            APELLIDO_MATERNO = P_APELLIDO_MATERNO,
            ID_DATOS_ASOCIADOS = P_ID_DATOS_ASOCIADOS,
            ID_ESTADO = P_ID_ESTADO,
            ID_DIRECCION = P_ID_DIRECCION,
            NOMBRE_USUARIO = P_NOMBRE_USUARIO,
            CONTRASENIA = P_CONTRASENIA
        WHERE ID_USUARIO = P_ID_USUARIO;
    
        DELETE FROM FIDE_USUARIOS_CORREOS_TB WHERE USUARIO_ID = P_ID_USUARIO;
        
        IF P_CORREOS_IDS IS NOT NULL THEN
            FOR i IN 1..P_CORREOS_IDS.COUNT LOOP
                INSERT INTO FIDE_USUARIOS_CORREOS_TB (USUARIO_ID, CORREO_ID)
                VALUES (P_ID_USUARIO, P_CORREOS_IDS(i));
            END LOOP;
        END IF;
    
  
        DELETE FROM FIDE_USUARIOS_TELEFONOS_TB WHERE USUARIO_ID = P_ID_USUARIO;
        
        IF P_NUMEROS_IDS IS NOT NULL THEN
            FOR j IN 1..P_NUMEROS_IDS.COUNT LOOP
                INSERT INTO FIDE_USUARIOS_TELEFONOS_TB (USUARIO_ID, NUMERO_ID)
                VALUES (P_ID_USUARIO, P_NUMEROS_IDS(j));
            END LOOP;
        END IF;
    
        COMMIT;
    EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
            RAISE_APPLICATION_ERROR(-20002, 'Error al editar: ' || SQLERRM);
    END FIDE_USUARIOS_EDITAR_SP;

    PROCEDURE FIDE_USUARIOS_ELIMINAR_SP(
        P_ID_USUARIO IN FIDE_USUARIO_TB.ID_USUARIO%TYPE
    ) IS
        V_ESTADO_INACTIVO CONSTANT NUMBER := 2;
    BEGIN
        UPDATE FIDE_USUARIO_TB
        SET ID_ESTADO = V_ESTADO_INACTIVO
        WHERE ID_USUARIO = P_ID_USUARIO;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró el usuario con ID ' || P_ID_USUARIO);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Usuario eliminado exitosamente.');
        END IF;
    EXCEPTION
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Valor incorrecto en la eliminación.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al eliminar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_USUARIOS_ELIMINAR_SP;

    -- IMPLEMENTACIÓN PARA FIDE_ACTIVIDAD_TB

    PROCEDURE FIDE_ACTIVIDAD_INSERTAR_SP(
        P_NOMBRE IN FIDE_ACTIVIDAD_TB.NOMBRE%TYPE,
        P_DESCRIPCION IN FIDE_ACTIVIDAD_TB.DESCRIPCION%TYPE,
        P_FECHA_EVENTO IN FIDE_ACTIVIDAD_TB.FECHA_EVENTO%TYPE,
        P_CUPO_TOTAL IN FIDE_ACTIVIDAD_TB.CUPO_TOTAL%TYPE,
        P_ID_ESTADO IN FIDE_ACTIVIDAD_TB.ID_ESTADO%TYPE,
        P_ID_USUARIO IN FIDE_ACTIVIDAD_TB.ID_USUARIO%TYPE,
        P_ID_LUGAR_EVENTO IN FIDE_ACTIVIDAD_TB.ID_LUGAR_EVENTO%TYPE
    ) IS
    BEGIN
    
        IF P_FECHA_EVENTO < SYSDATE THEN
        RAISE_APPLICATION_ERROR(-20001,
            'La fecha del evento no puede ser en el pasado');
        END IF;
        
        IF P_CUPO_TOTAL <= 0 THEN
            RAISE_APPLICATION_ERROR(-20002,
                'El cupo total debe ser mayor a 0');
        END IF;

    
        INSERT INTO FIDE_ACTIVIDAD_TB(
            NOMBRE, DESCRIPCION, FECHA_EVENTO, CUPO_TOTAL,
            ID_ESTADO, ID_USUARIO, ID_LUGAR_EVENTO
        ) VALUES (
            P_NOMBRE, P_DESCRIPCION, P_FECHA_EVENTO, P_CUPO_TOTAL,
            P_ID_ESTADO, P_ID_USUARIO, P_ID_LUGAR_EVENTO
        );
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Registro insertado correctamente en FIDE_ACTIVIDAD_TB.');
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Ya existe una actividad con esos datos.');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en la actividad.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_ACTIVIDAD_INSERTAR_SP;

    PROCEDURE FIDE_ACTIVIDAD_EDITAR_SP(
        P_ID_ACTIVIDAD IN FIDE_ACTIVIDAD_TB.ID_ACTIVIDAD%TYPE,
        P_NOMBRE IN FIDE_ACTIVIDAD_TB.NOMBRE%TYPE,
        P_DESCRIPCION IN FIDE_ACTIVIDAD_TB.DESCRIPCION%TYPE,
        P_FECHA_EVENTO IN FIDE_ACTIVIDAD_TB.FECHA_EVENTO%TYPE,
        P_CUPO_TOTAL IN FIDE_ACTIVIDAD_TB.CUPO_TOTAL%TYPE,
        P_ID_ESTADO IN FIDE_ACTIVIDAD_TB.ID_ESTADO%TYPE,
        P_ID_USUARIO IN FIDE_ACTIVIDAD_TB.ID_USUARIO%TYPE,
        P_ID_LUGAR_EVENTO IN FIDE_ACTIVIDAD_TB.ID_LUGAR_EVENTO%TYPE
    ) IS
    BEGIN
    
        IF P_FECHA_EVENTO < SYSDATE THEN
            RAISE_APPLICATION_ERROR(-20001, 'La fecha del evento no puede ser en el pasado');
        END IF;
        
        IF P_CUPO_TOTAL <= 0 THEN
            RAISE_APPLICATION_ERROR(-20002, 'El cupo total debe ser mayor a 0');
        END IF;
    
        UPDATE FIDE_ACTIVIDAD_TB
        SET NOMBRE = P_NOMBRE,
            DESCRIPCION = P_DESCRIPCION,
            FECHA_EVENTO = P_FECHA_EVENTO,
            CUPO_TOTAL = P_CUPO_TOTAL,
            ID_ESTADO = P_ID_ESTADO,
            ID_USUARIO = P_ID_USUARIO,
            ID_LUGAR_EVENTO = P_ID_LUGAR_EVENTO
        WHERE ID_ACTIVIDAD = P_ID_ACTIVIDAD;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró la actividad con ID ' || P_ID_ACTIVIDAD);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Actividad actualizada exitosamente.');
        END IF;
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Ya existe una actividad con ese nombre.');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en la actualización.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al actualizar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_ACTIVIDAD_EDITAR_SP;
    
    

    PROCEDURE FIDE_ACTIVIDAD_ELIMINAR_SP(
        P_ID_ACTIVIDAD IN FIDE_ACTIVIDAD_TB.ID_ACTIVIDAD%TYPE
    ) IS
        V_ESTADO_INACTIVO CONSTANT NUMBER := 2;
    BEGIN
        UPDATE FIDE_ACTIVIDAD_TB
        SET ID_ESTADO = V_ESTADO_INACTIVO
        WHERE ID_ACTIVIDAD = P_ID_ACTIVIDAD;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró la actividad con ID ' || P_ID_ACTIVIDAD);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Actividad eliminada exitosamente.');
        END IF;
    EXCEPTION
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Valor incorrecto en la eliminación.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al eliminar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_ACTIVIDAD_ELIMINAR_SP;

    -- IMPLEMENTACIÓN PARA FIDE_BENEFICIO_TB

    PROCEDURE FIDE_BENEFICIO_INSERTAR_SP(
        P_NOMBRE_BENEFICIO IN FIDE_BENEFICIO_TB.NOMBRE_BENEFICIO%TYPE,
        P_DESCRIPCION IN FIDE_BENEFICIO_TB.DESCRIPCION%TYPE,
        P_ID_CATEGORIA IN FIDE_BENEFICIO_TB.ID_CATEGORIA%TYPE,
        P_ID_ESTADO IN FIDE_BENEFICIO_TB.ID_ESTADO%TYPE
    ) IS
    BEGIN
        INSERT INTO FIDE_BENEFICIO_TB(
            NOMBRE_BENEFICIO, DESCRIPCION, ID_CATEGORIA, ID_ESTADO
        ) VALUES (
            P_NOMBRE_BENEFICIO, P_DESCRIPCION, P_ID_CATEGORIA, P_ID_ESTADO
        );
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Registro insertado correctamente en FIDE_BENEFICIO_TB.');
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: El beneficio ' || P_NOMBRE_BENEFICIO || ' ya existe.');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en beneficio.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_BENEFICIO_INSERTAR_SP;

    PROCEDURE FIDE_BENEFICIO_EDITAR_SP(
        P_ID_BENEFICIO IN FIDE_BENEFICIO_TB.ID_BENEFICIO%TYPE,
        P_NOMBRE_BENEFICIO IN FIDE_BENEFICIO_TB.NOMBRE_BENEFICIO%TYPE,
        P_DESCRIPCION IN FIDE_BENEFICIO_TB.DESCRIPCION%TYPE,
        P_ID_CATEGORIA IN FIDE_BENEFICIO_TB.ID_CATEGORIA%TYPE,
        P_ID_ESTADO IN FIDE_BENEFICIO_TB.ID_ESTADO%TYPE
    ) IS
    BEGIN
        UPDATE FIDE_BENEFICIO_TB
        SET NOMBRE_BENEFICIO = P_NOMBRE_BENEFICIO,
            DESCRIPCION = P_DESCRIPCION,
            ID_CATEGORIA = P_ID_CATEGORIA,
            ID_ESTADO = P_ID_ESTADO
        WHERE ID_BENEFICIO = P_ID_BENEFICIO;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró el beneficio con ID ' || P_ID_BENEFICIO);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Beneficio actualizado exitosamente.');
        END IF;
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: El nombre del beneficio ya existe.');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en la actualización.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al actualizar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_BENEFICIO_EDITAR_SP;

    PROCEDURE FIDE_BENEFICIO_ELIMINAR_SP(
        P_ID_BENEFICIO IN FIDE_BENEFICIO_TB.ID_BENEFICIO%TYPE
    ) IS
        V_ESTADO_INACTIVO CONSTANT NUMBER := 2;
    BEGIN
        UPDATE FIDE_BENEFICIO_TB
        SET ID_ESTADO = V_ESTADO_INACTIVO
        WHERE ID_BENEFICIO = P_ID_BENEFICIO;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró el beneficio con ID ' || P_ID_BENEFICIO);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Beneficio eliminado exitosamente.');
        END IF;
    EXCEPTION
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Valor incorrecto en la eliminación.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al eliminar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_BENEFICIO_ELIMINAR_SP;

    -- IMPLEMENTACIÓN PARA FIDE_CATEGORIA_TB

    PROCEDURE FIDE_CATEGORIA_INSERTAR_SP(
        P_NOMBRE IN FIDE_CATEGORIA_TB.NOMBRE%TYPE,
        P_DESCRIPCION IN FIDE_CATEGORIA_TB.DESCRIPCION%TYPE,
        P_ID_ESTADO IN FIDE_CATEGORIA_TB.ID_ESTADO%TYPE
    ) IS
    BEGIN
        INSERT INTO FIDE_CATEGORIA_TB(NOMBRE, DESCRIPCION, ID_ESTADO)
        VALUES (P_NOMBRE, P_DESCRIPCION, P_ID_ESTADO);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Registro insertado correctamente en FIDE_CATEGORIA_TB.');
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: La categoría ' || P_NOMBRE || ' ya existe.');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en categoría.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_CATEGORIA_INSERTAR_SP;

    PROCEDURE FIDE_CATEGORIA_EDITAR_SP(
        P_ID_CATEGORIA IN FIDE_CATEGORIA_TB.ID_CATEGORIA%TYPE,
        P_NOMBRE IN FIDE_CATEGORIA_TB.NOMBRE%TYPE,
        P_DESCRIPCION IN FIDE_CATEGORIA_TB.DESCRIPCION%TYPE,
        P_ID_ESTADO IN FIDE_CATEGORIA_TB.ID_ESTADO%TYPE
    ) IS
    BEGIN
        UPDATE FIDE_CATEGORIA_TB
        SET NOMBRE = P_NOMBRE,
            DESCRIPCION = P_DESCRIPCION,
            ID_ESTADO = P_ID_ESTADO
        WHERE ID_CATEGORIA = P_ID_CATEGORIA;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró la categoría con ID ' || P_ID_CATEGORIA);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Categoría actualizada exitosamente.');
        END IF;
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: El nombre de la categoría ya existe.');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en la actualización.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al actualizar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_CATEGORIA_EDITAR_SP;

    PROCEDURE FIDE_CATEGORIA_ELIMINAR_SP(
        P_ID_CATEGORIA IN FIDE_CATEGORIA_TB.ID_CATEGORIA%TYPE
    ) IS
        V_ESTADO_INACTIVO CONSTANT NUMBER := 2;
    BEGIN
        UPDATE FIDE_CATEGORIA_TB
        SET ID_ESTADO = V_ESTADO_INACTIVO
        WHERE ID_CATEGORIA = P_ID_CATEGORIA;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró la categoría con ID ' || P_ID_CATEGORIA);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Categoría eliminada exitosamente.');
        END IF;
    EXCEPTION
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Valor incorrecto en la eliminación.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al eliminar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_CATEGORIA_ELIMINAR_SP;

    -- IMPLEMENTACIÓN PARA FIDE_CORREO_ELECTRONICO_TB

    PROCEDURE FIDE_CORREO_INSERTAR_SP(
    P_CORREO_ELECTRONICO IN FIDE_CORREO_ELECTRONICO_TB.CORREO_ELECTRONICO%TYPE,
    P_ID_ESTADO IN FIDE_CORREO_ELECTRONICO_TB.ID_ESTADO%TYPE
    ) IS
    BEGIN
        INSERT INTO FIDE_CORREO_ELECTRONICO_TB
        (ID_CORREO, CORREO_ELECTRONICO, ID_ESTADO)
        VALUES
        (SEQ_CORREO.NEXTVAL, P_CORREO_ELECTRONICO, P_ID_ESTADO);

        COMMIT;

        DBMS_OUTPUT.PUT_LINE('Registro insertado correctamente en FIDE_CORREO_ELECTRONICO_TB.');

    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: El correo ' || P_CORREO_ELECTRONICO || ' ya existe.');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de correo incorrecto o valor nulo.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_CORREO_INSERTAR_SP;

    PROCEDURE FIDE_CORREO_EDITAR_SP(
        P_ID_CORREO IN FIDE_CORREO_ELECTRONICO_TB.ID_CORREO%TYPE,
        P_CORREO_ELECTRONICO IN FIDE_CORREO_ELECTRONICO_TB.CORREO_ELECTRONICO%TYPE,
        P_ID_ESTADO IN FIDE_CORREO_ELECTRONICO_TB.ID_ESTADO%TYPE
    ) IS
    BEGIN
        UPDATE FIDE_CORREO_ELECTRONICO_TB
        SET CORREO_ELECTRONICO = P_CORREO_ELECTRONICO,
            ID_ESTADO = P_ID_ESTADO
        WHERE ID_CORREO = P_ID_CORREO;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró el correo con ID ' || P_ID_CORREO);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Correo actualizado exitosamente.');
        END IF;
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: El correo ' || P_CORREO_ELECTRONICO || ' ya está registrado.');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en la actualización.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al actualizar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_CORREO_EDITAR_SP;

    PROCEDURE FIDE_CORREO_ELIMINAR_SP(
        P_ID_CORREO IN FIDE_CORREO_ELECTRONICO_TB.ID_CORREO%TYPE
    ) IS
        V_ESTADO_INACTIVO CONSTANT NUMBER := 2;
    BEGIN
        UPDATE FIDE_CORREO_ELECTRONICO_TB
        SET ID_ESTADO = V_ESTADO_INACTIVO
        WHERE ID_CORREO = P_ID_CORREO;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró el correo con ID ' || P_ID_CORREO);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Correo eliminado exitosamente.');
        END IF;
    EXCEPTION
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Valor incorrecto en la eliminación.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al eliminar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_CORREO_ELIMINAR_SP;

    -- IMPLEMENTACIÓN PARA FIDE_CUENTAS_AHORRO_TB

    PROCEDURE FIDE_CUENTAS_AHORRO_INSERTAR_SP(
        P_ID_USUARIO     IN FIDE_CUENTAS_AHORRO_TB.ID_USUARIO%TYPE,
        P_MONTO_APORTE   IN FIDE_CUENTAS_AHORRO_TB.MONTO_APORTE%TYPE,
        P_FECHA_APERTURA IN FIDE_CUENTAS_AHORRO_TB.FECHA_APERTURA%TYPE,
        P_ID_TIPO_AHORRO IN FIDE_CUENTAS_AHORRO_TB.ID_TIPO_AHORRO%TYPE,
        P_SALDO_ACTUAL   IN FIDE_CUENTAS_AHORRO_TB.SALDO_ACTUAL%TYPE,
        P_ID_ESTADO      IN FIDE_CUENTAS_AHORRO_TB.ID_ESTADO%TYPE,
        P_ID_AHORRO      OUT FIDE_CUENTAS_AHORRO_TB.ID_AHORRO%TYPE
    ) IS
        V_CUENTA_EXISTENTE NUMBER;
        V_USUARIO_EXISTE   NUMBER;
    BEGIN
        -- Verificar que el usuario existe
        SELECT COUNT(*) INTO V_USUARIO_EXISTE
        FROM FIDE_USUARIO_TB
        WHERE ID_USUARIO = P_ID_USUARIO;
    
        IF V_USUARIO_EXISTE = 0 THEN
            RAISE_APPLICATION_ERROR(-20001,
                'No existe el usuario con ID: ' || P_ID_USUARIO);
        END IF;
    
        -- Verificar que el usuario no tenga ya una cuenta activa del mismo tipo
        SELECT COUNT(*) INTO V_CUENTA_EXISTENTE
        FROM FIDE_CUENTAS_AHORRO_TB
        WHERE ID_USUARIO = P_ID_USUARIO
          AND ID_TIPO_AHORRO = P_ID_TIPO_AHORRO
          AND ID_ESTADO = 1;
    
        IF V_CUENTA_EXISTENTE > 0 THEN
            RAISE_APPLICATION_ERROR(-20002,
                'El usuario ya tiene una cuenta activa de este tipo');
        END IF;
    
        -- Validar que el monto inicial no sea negativo
        IF P_SALDO_ACTUAL < 0 THEN
            RAISE_APPLICATION_ERROR(-20003,
                'El saldo inicial no puede ser negativo');
        END IF;
    
        INSERT INTO FIDE_CUENTAS_AHORRO_TB(
            ID_USUARIO, MONTO_APORTE, FECHA_APERTURA,
            ID_TIPO_AHORRO, SALDO_ACTUAL, ID_ESTADO
        ) VALUES (
            P_ID_USUARIO, P_MONTO_APORTE, P_FECHA_APERTURA,
            P_ID_TIPO_AHORRO, P_SALDO_ACTUAL, P_ID_ESTADO
        ) RETURNING ID_AHORRO INTO P_ID_AHORRO;
    
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Cuenta de ahorro creada correctamente. ID: ' || P_ID_AHORRO);
    
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: La cuenta de ahorro ya existe.');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en cuenta de ahorro.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_CUENTAS_AHORRO_INSERTAR_SP;
    
    PROCEDURE FIDE_CUENTAS_AHORRO_EDITAR_SP(
        P_ID_AHORRO IN FIDE_CUENTAS_AHORRO_TB.ID_AHORRO%TYPE,
        P_ID_USUARIO IN FIDE_CUENTAS_AHORRO_TB.ID_USUARIO%TYPE,
        P_MONTO_APORTE IN FIDE_CUENTAS_AHORRO_TB.MONTO_APORTE%TYPE,
        P_FECHA_APERTURA IN FIDE_CUENTAS_AHORRO_TB.FECHA_APERTURA%TYPE,
        P_ID_TIPO_AHORRO IN FIDE_CUENTAS_AHORRO_TB.ID_TIPO_AHORRO%TYPE,
        P_SALDO_ACTUAL IN FIDE_CUENTAS_AHORRO_TB.SALDO_ACTUAL%TYPE,
        P_ID_ESTADO IN FIDE_CUENTAS_AHORRO_TB.ID_ESTADO%TYPE
    ) IS
    BEGIN
        UPDATE FIDE_CUENTAS_AHORRO_TB
        SET ID_USUARIO = P_ID_USUARIO,
            MONTO_APORTE = P_MONTO_APORTE,
            FECHA_APERTURA = P_FECHA_APERTURA,
            ID_TIPO_AHORRO = P_ID_TIPO_AHORRO,
            SALDO_ACTUAL = P_SALDO_ACTUAL,
            ID_ESTADO = P_ID_ESTADO
        WHERE ID_AHORRO = P_ID_AHORRO;
        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró la cuenta de ahorro con ID ' || P_ID_AHORRO);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Cuenta de ahorro actualizada exitosamente.');
        END IF;
    EXCEPTION
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en la actualización.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al actualizar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_CUENTAS_AHORRO_EDITAR_SP;

    PROCEDURE FIDE_CUENTAS_AHORRO_ELIMINAR_SP(
        P_ID_AHORRO IN FIDE_CUENTAS_AHORRO_TB.ID_AHORRO%TYPE
    ) IS
        V_ESTADO_INACTIVO CONSTANT NUMBER := 2;
    BEGIN
        UPDATE FIDE_CUENTAS_AHORRO_TB
        SET ID_ESTADO = V_ESTADO_INACTIVO
        WHERE ID_AHORRO = P_ID_AHORRO;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró la cuenta de ahorro con ID ' || P_ID_AHORRO);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Cuenta de ahorro eliminada exitosamente.');
        END IF;
    EXCEPTION
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Valor incorrecto en la eliminación.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al eliminar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_CUENTAS_AHORRO_ELIMINAR_SP;

    -- IMPLEMENTACIÓN PARA FIDE_DATOS_ASOCIADOS_TB

   PROCEDURE FIDE_DATOS_ASOCIADOS_INSERTAR_SP(
        P_ID_PUESTO_EMPRESA IN FIDE_DATOS_ASOCIADOS_TB.ID_PUESTO_EMPRESA%TYPE,
        P_FECHA_AFILIACION  IN FIDE_DATOS_ASOCIADOS_TB.FECHA_AFILIACION%TYPE,
        P_APORTES           IN SYS.ODCINUMBERLIST,  -- lista de montos
        P_ID_DATOS_ASOCIADOS OUT FIDE_DATOS_ASOCIADOS_TB.ID_DATOS_ASOCIADOS%TYPE
    ) IS
        V_ID_DATOS_ASOCIADOS FIDE_DATOS_ASOCIADOS_TB.ID_DATOS_ASOCIADOS%TYPE;
    BEGIN
        INSERT INTO FIDE_DATOS_ASOCIADOS_TB(ID_PUESTO_EMPRESA, FECHA_AFILIACION)
        VALUES (P_ID_PUESTO_EMPRESA, P_FECHA_AFILIACION)
        RETURNING ID_DATOS_ASOCIADOS INTO V_ID_DATOS_ASOCIADOS;

        -- Insertar cada aporte de la lista
        FOR I IN 1..P_APORTES.COUNT LOOP
            INSERT INTO FIDE_APORTE_TB(ID_DATOS_ASOCIADOS, MONTO, FECHA_INICIO, FECHA_FIN)
            VALUES (V_ID_DATOS_ASOCIADOS, P_APORTES(I), P_FECHA_AFILIACION, NULL);
        END LOOP;

        P_ID_DATOS_ASOCIADOS := V_ID_DATOS_ASOCIADOS;
        COMMIT;
    EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_DATOS_ASOCIADOS_INSERTAR_SP; 

   PROCEDURE FIDE_DATOS_ASOCIADOS_EDITAR_SP(
        P_ID_DATOS_ASOCIADOS IN FIDE_DATOS_ASOCIADOS_TB.ID_DATOS_ASOCIADOS%TYPE,
        P_ID_PUESTO_EMPRESA  IN FIDE_DATOS_ASOCIADOS_TB.ID_PUESTO_EMPRESA%TYPE,
        P_FECHA_AFILIACION   IN FIDE_DATOS_ASOCIADOS_TB.FECHA_AFILIACION%TYPE,
        P_APORTES            IN SYS.ODCINUMBERLIST
    ) IS
    BEGIN
        UPDATE FIDE_DATOS_ASOCIADOS_TB
        SET ID_PUESTO_EMPRESA = P_ID_PUESTO_EMPRESA,
            FECHA_AFILIACION  = P_FECHA_AFILIACION
        WHERE ID_DATOS_ASOCIADOS = P_ID_DATOS_ASOCIADOS;
    
        IF SQL%ROWCOUNT = 0 THEN
            RAISE_APPLICATION_ERROR(-20001,
                'No existe el dato asociado con ID: ' || P_ID_DATOS_ASOCIADOS);
        END IF;
    
        -- Cerrar aportes vigentes
        UPDATE FIDE_APORTE_TB
        SET FECHA_FIN = SYSDATE
        WHERE ID_DATOS_ASOCIADOS = P_ID_DATOS_ASOCIADOS
          AND FECHA_FIN IS NULL;
    
        -- Insertar nuevos aportes
        FOR I IN 1..P_APORTES.COUNT LOOP
            INSERT INTO FIDE_APORTE_TB(ID_DATOS_ASOCIADOS, MONTO, FECHA_INICIO, FECHA_FIN)
            VALUES (P_ID_DATOS_ASOCIADOS, P_APORTES(I), SYSDATE, NULL);
        END LOOP;
    
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Dato asociado actualizado correctamente.');
    
    EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_DATOS_ASOCIADOS_EDITAR_SP;
    
    

    PROCEDURE FIDE_DATOS_ASOCIADOS_ELIMINAR_SP(
        P_ID_DATOS_ASOCIADOS IN FIDE_DATOS_ASOCIADOS_TB.ID_DATOS_ASOCIADOS%TYPE
    ) IS
    BEGIN
        DELETE FROM FIDE_DATOS_ASOCIADOS_TB
        WHERE ID_DATOS_ASOCIADOS = P_ID_DATOS_ASOCIADOS;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontraron datos asociados con ID ' || P_ID_DATOS_ASOCIADOS);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Datos asociados eliminados físicamente.');
        END IF;
    EXCEPTION
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Valor incorrecto en la eliminación.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al eliminar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_DATOS_ASOCIADOS_ELIMINAR_SP;

    -- IMPLEMENTACIÓN PARA FIDE_DIRECCION_TB

    PROCEDURE FIDE_DIRECCION_INSERTAR_SP(
        P_PROVINCIA IN FIDE_DIRECCION_TB.PROVINCIA%TYPE,
        P_CANTON IN FIDE_DIRECCION_TB.CANTON%TYPE,
        P_DISTRITO IN FIDE_DIRECCION_TB.DISTRITO%TYPE,
        P_ID_ESTADO IN FIDE_DIRECCION_TB.ID_ESTADO%TYPE
    ) IS
    BEGIN
        INSERT INTO FIDE_DIRECCION_TB(PROVINCIA, CANTON, DISTRITO, ID_ESTADO)
        VALUES (P_PROVINCIA, P_CANTON, P_DISTRITO, P_ID_ESTADO);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Registro insertado correctamente en FIDE_DIRECCION_TB.');
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: La dirección ya existe.');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en dirección.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_DIRECCION_INSERTAR_SP;

    PROCEDURE FIDE_DIRECCION_EDITAR_SP(
        P_ID_DIRECCION IN FIDE_DIRECCION_TB.ID_DIRECCION%TYPE,
        P_PROVINCIA IN FIDE_DIRECCION_TB.PROVINCIA%TYPE,
        P_CANTON IN FIDE_DIRECCION_TB.CANTON%TYPE,
        P_DISTRITO IN FIDE_DIRECCION_TB.DISTRITO%TYPE,
        P_ID_ESTADO IN FIDE_DIRECCION_TB.ID_ESTADO%TYPE
    ) IS
    BEGIN
        UPDATE FIDE_DIRECCION_TB
        SET PROVINCIA = P_PROVINCIA,
            CANTON = P_CANTON,
            DISTRITO = P_DISTRITO,
            ID_ESTADO = P_ID_ESTADO
        WHERE ID_DIRECCION = P_ID_DIRECCION;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró la dirección con ID ' || P_ID_DIRECCION);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Dirección actualizada exitosamente.');
        END IF;
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Ya existe una dirección con esos datos.');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en la actualización.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al actualizar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_DIRECCION_EDITAR_SP;

    PROCEDURE FIDE_DIRECCION_ELIMINAR_SP(
        P_ID_DIRECCION IN FIDE_DIRECCION_TB.ID_DIRECCION%TYPE
    ) IS
        V_ESTADO_INACTIVO CONSTANT NUMBER := 2;
    BEGIN
        UPDATE FIDE_DIRECCION_TB
        SET ID_ESTADO = V_ESTADO_INACTIVO
        WHERE ID_DIRECCION = P_ID_DIRECCION;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró la dirección con ID ' || P_ID_DIRECCION);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Dirección eliminada exitosamente.');
        END IF;
    EXCEPTION
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Valor incorrecto en la eliminación.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al eliminar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_DIRECCION_ELIMINAR_SP;

    -- IMPLEMENTACIÓN PARA FIDE_ESTADO_TB

    PROCEDURE FIDE_ESTADO_INSERTAR_SP(
        P_NOMBRE IN FIDE_ESTADO_TB.NOMBRE%TYPE
    ) IS
    BEGIN
        INSERT INTO FIDE_ESTADO_TB(NOMBRE)
        VALUES (P_NOMBRE);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Registro insertado correctamente en FIDE_ESTADO_TB.');
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: El estado ' || P_NOMBRE || ' ya existe.');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en estado.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_ESTADO_INSERTAR_SP;

    PROCEDURE FIDE_ESTADO_EDITAR_SP(
        P_ID_ESTADO IN FIDE_ESTADO_TB.ID_ESTADO%TYPE,
        P_NOMBRE IN FIDE_ESTADO_TB.NOMBRE%TYPE
    ) IS
    BEGIN
        UPDATE FIDE_ESTADO_TB
        SET NOMBRE = P_NOMBRE
        WHERE ID_ESTADO = P_ID_ESTADO;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró el estado con ID ' || P_ID_ESTADO);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Estado actualizado exitosamente.');
        END IF;
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: El nombre del estado ya existe.');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en la actualización.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al actualizar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_ESTADO_EDITAR_SP;

    PROCEDURE FIDE_ESTADO_ELIMINAR_SP(
        P_ID_ESTADO IN FIDE_ESTADO_TB.ID_ESTADO%TYPE
    ) IS
    BEGIN
        DELETE FROM FIDE_ESTADO_TB
        WHERE ID_ESTADO = P_ID_ESTADO;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró el estado con ID ' || P_ID_ESTADO);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Estado eliminado físicamente.');
        END IF;
    EXCEPTION
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Valor incorrecto en la eliminación.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al eliminar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_ESTADO_ELIMINAR_SP;

    -- IMPLEMENTACIÓN PARA FIDE_INSCRIPCIONES_ACTIVIDAD_TB

    PROCEDURE FIDE_INSCRIPCIONES_ACTIVIDAD_INSERTAR_SP(
        P_ID_ACTIVIDAD          IN FIDE_INSCRIPCIONES_ACTIVIDAD_TB.ID_ACTIVIDAD%TYPE,
        P_ID_USUARIO            IN FIDE_INSCRIPCIONES_ACTIVIDAD_TB.ID_USUARIO%TYPE,
        P_FECHA_INSCRIPCION     IN FIDE_INSCRIPCIONES_ACTIVIDAD_TB.FECHA_INSCRIPCION%TYPE,
        P_ASISTENCIA_CONFIRMADA IN FIDE_INSCRIPCIONES_ACTIVIDAD_TB.ASISTENCIA_CONFIRMADA%TYPE,
        P_ID_ESTADO             IN FIDE_INSCRIPCIONES_ACTIVIDAD_TB.ID_ESTADO%TYPE
    ) IS
        V_CUPO_TOTAL       FIDE_ACTIVIDAD_TB.CUPO_TOTAL%TYPE;
        V_INSCRITOS        NUMBER;
        V_YA_INSCRITO      NUMBER;
        V_ESTADO_USUARIO   NUMBER;
    BEGIN
        -- Validar que el usuario no esté ya inscrito
        SELECT COUNT(*) INTO V_YA_INSCRITO
        FROM FIDE_INSCRIPCIONES_ACTIVIDAD_TB
        WHERE ID_ACTIVIDAD = P_ID_ACTIVIDAD
          AND ID_USUARIO = P_ID_USUARIO
          AND ID_ESTADO = 1;
    
        IF V_YA_INSCRITO > 0 THEN
            RAISE_APPLICATION_ERROR(-20001,
                'El usuario ya está inscrito en esta actividad');
        END IF;
        
        SELECT ID_ESTADO INTO V_ESTADO_USUARIO
        FROM FIDE_USUARIO_TB        
        WHERE ID_USUARIO = P_ID_USUARIO;
    
        IF V_ESTADO_USUARIO = 2 THEN  
            RAISE_APPLICATION_ERROR(-20004, 'El usuario se encuentra inactivo en el sistema y no puede inscribirse.');
        END IF;
    
        -- Obtener cupo total de la actividad
        SELECT CUPO_TOTAL INTO V_CUPO_TOTAL
        FROM FIDE_ACTIVIDAD_TB
        WHERE ID_ACTIVIDAD = P_ID_ACTIVIDAD;
    
        -- Contar inscritos actuales
        SELECT COUNT(*) INTO V_INSCRITOS
        FROM FIDE_INSCRIPCIONES_ACTIVIDAD_TB
        WHERE ID_ACTIVIDAD = P_ID_ACTIVIDAD
          AND ID_ESTADO = 1;
    
        -- Validar que haya cupo disponible
        IF V_INSCRITOS >= V_CUPO_TOTAL THEN
            RAISE_APPLICATION_ERROR(-20002,
                'No hay cupo disponible para esta actividad');
        END IF;
    
        INSERT INTO FIDE_INSCRIPCIONES_ACTIVIDAD_TB(
            ID_ACTIVIDAD, ID_USUARIO, FECHA_INSCRIPCION, ASISTENCIA_CONFIRMADA, ID_ESTADO
        ) VALUES (
            P_ID_ACTIVIDAD, P_ID_USUARIO, P_FECHA_INSCRIPCION, P_ASISTENCIA_CONFIRMADA, P_ID_ESTADO
        );
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Inscripción registrada correctamente.');
    
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            ROLLBACK;
            RAISE_APPLICATION_ERROR(-20003, 'No existe la actividad con ID: ' || P_ID_ACTIVIDAD);
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en inscripción.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_INSCRIPCIONES_ACTIVIDAD_INSERTAR_SP;

    PROCEDURE FIDE_INSCRIPCIONES_ACTIVIDAD_EDITAR_SP(
        P_ID_INSCRIPCION IN FIDE_INSCRIPCIONES_ACTIVIDAD_TB.ID_INSCRIPCION%TYPE,
        P_ID_ACTIVIDAD IN FIDE_INSCRIPCIONES_ACTIVIDAD_TB.ID_ACTIVIDAD%TYPE,
        P_ID_USUARIO IN FIDE_INSCRIPCIONES_ACTIVIDAD_TB.ID_USUARIO%TYPE,
        P_FECHA_INSCRIPCION IN FIDE_INSCRIPCIONES_ACTIVIDAD_TB.FECHA_INSCRIPCION%TYPE,
        P_ASISTENCIA_CONFIRMADA IN FIDE_INSCRIPCIONES_ACTIVIDAD_TB.ASISTENCIA_CONFIRMADA%TYPE,
        P_ID_ESTADO IN FIDE_INSCRIPCIONES_ACTIVIDAD_TB.ID_ESTADO%TYPE
    ) IS
    BEGIN
        UPDATE FIDE_INSCRIPCIONES_ACTIVIDAD_TB
        SET ID_ACTIVIDAD = P_ID_ACTIVIDAD,
            ID_USUARIO = P_ID_USUARIO,
            FECHA_INSCRIPCION = P_FECHA_INSCRIPCION,
            ASISTENCIA_CONFIRMADA = P_ASISTENCIA_CONFIRMADA,
            ID_ESTADO = P_ID_ESTADO
        WHERE ID_INSCRIPCION = P_ID_INSCRIPCION;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró la inscripción con ID ' || P_ID_INSCRIPCION);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Inscripción actualizada exitosamente.');
        END IF;
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: El usuario ya está inscrito en esta actividad.');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en la actualización.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al actualizar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_INSCRIPCIONES_ACTIVIDAD_EDITAR_SP;

    PROCEDURE FIDE_INSCRIPCIONES_ACTIVIDAD_ELIMINAR_SP(
        P_ID_INSCRIPCION IN FIDE_INSCRIPCIONES_ACTIVIDAD_TB.ID_INSCRIPCION%TYPE
    ) IS
       V_ESTADO_INACTIVO CONSTANT NUMBER := 2;
       V_ESTADO_ACTUAL   FIDE_INSCRIPCIONES_ACTIVIDAD_TB.ID_ESTADO%TYPE;
    BEGIN
    
        SELECT ID_ESTADO INTO V_ESTADO_ACTUAL
        FROM FIDE_INSCRIPCIONES_ACTIVIDAD_TB
        WHERE ID_INSCRIPCION = P_ID_INSCRIPCION;
    
        -- Validar que no esté ya cancelada
        IF V_ESTADO_ACTUAL = V_ESTADO_INACTIVO THEN
            RAISE_APPLICATION_ERROR(-20001,
                'La inscripción ya está cancelada');
        END IF;
    
        UPDATE FIDE_INSCRIPCIONES_ACTIVIDAD_TB
        SET ID_ESTADO = V_ESTADO_INACTIVO
        WHERE ID_INSCRIPCION = P_ID_INSCRIPCION;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró la inscripción con ID ' || P_ID_INSCRIPCION);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Inscripción eliminada exitosamente.');
        END IF;
    EXCEPTION
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Valor incorrecto en la eliminación.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al eliminar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_INSCRIPCIONES_ACTIVIDAD_ELIMINAR_SP;

    -- IMPLEMENTACIÓN PARA FIDE_LUGAR_EVENTO_TB

    PROCEDURE FIDE_LUGAR_EVENTO_INSERTAR_SP(
        P_NOMBRE_LUGAR IN FIDE_LUGAR_EVENTO_TB.NOMBRE_LUGAR%TYPE,
        P_ID_ESTADO IN FIDE_LUGAR_EVENTO_TB.ID_ESTADO%TYPE
    ) IS
    BEGIN
        INSERT INTO FIDE_LUGAR_EVENTO_TB(NOMBRE_LUGAR, ID_ESTADO)
        VALUES (P_NOMBRE_LUGAR, P_ID_ESTADO);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Registro insertado correctamente en FIDE_LUGAR_EVENTO_TB.');
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: El lugar de evento ' || P_NOMBRE_LUGAR || ' ya existe.');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en lugar de evento.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_LUGAR_EVENTO_INSERTAR_SP;

    PROCEDURE FIDE_LUGAR_EVENTO_EDITAR_SP(
        P_ID_LUGAR_EVENTO IN FIDE_LUGAR_EVENTO_TB.ID_LUGAR_EVENTO%TYPE,
        P_NOMBRE_LUGAR IN FIDE_LUGAR_EVENTO_TB.NOMBRE_LUGAR%TYPE,
        P_ID_ESTADO IN FIDE_LUGAR_EVENTO_TB.ID_ESTADO%TYPE
    ) IS
        V_NOMBRE_DUPLICADO NUMBER;
        V_ESTADO_ACTUAL    FIDE_LUGAR_EVENTO_TB.ID_ESTADO%TYPE;
    BEGIN
    
        -- Validar que el nombre no esté duplicado en otro registro
        SELECT COUNT(*) INTO V_NOMBRE_DUPLICADO
        FROM FIDE_LUGAR_EVENTO_TB
        WHERE UPPER(NOMBRE_LUGAR) = UPPER(P_NOMBRE_LUGAR)
          AND ID_LUGAR_EVENTO != P_ID_LUGAR_EVENTO;
    
        IF V_NOMBRE_DUPLICADO > 0 THEN
            RAISE_APPLICATION_ERROR(-20001,
                'Ya existe un lugar con el nombre: ' || P_NOMBRE_LUGAR);
        END IF;
    
        UPDATE FIDE_LUGAR_EVENTO_TB
        SET NOMBRE_LUGAR = P_NOMBRE_LUGAR,
            ID_ESTADO = P_ID_ESTADO
        WHERE ID_LUGAR_EVENTO = P_ID_LUGAR_EVENTO;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró el lugar de evento con ID ' || P_ID_LUGAR_EVENTO);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Lugar de evento actualizado exitosamente.');
        END IF;
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: El nombre del lugar ya existe.');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en la actualización.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al actualizar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_LUGAR_EVENTO_EDITAR_SP;

    PROCEDURE FIDE_LUGAR_EVENTO_ELIMINAR_SP(
        P_ID_LUGAR_EVENTO IN FIDE_LUGAR_EVENTO_TB.ID_LUGAR_EVENTO%TYPE
    ) IS
        V_ESTADO_INACTIVO CONSTANT NUMBER := 2;
    BEGIN
        UPDATE FIDE_LUGAR_EVENTO_TB
        SET ID_ESTADO = V_ESTADO_INACTIVO
        WHERE ID_LUGAR_EVENTO = P_ID_LUGAR_EVENTO;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró el lugar de evento con ID ' || P_ID_LUGAR_EVENTO);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Lugar de evento eliminado exitosamente.');
        END IF;
    EXCEPTION
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Valor incorrecto en la eliminación.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al eliminar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_LUGAR_EVENTO_ELIMINAR_SP;

    -- IMPLEMENTACIÓN PARA FIDE_MODULO_TB

    PROCEDURE FIDE_MODULO_INSERTAR_SP(
        P_NOMBRE IN FIDE_MODULO_TB.NOMBRE%TYPE,
        P_DESCRIPCION IN FIDE_MODULO_TB.DESCRIPCION%TYPE,
        P_ID_ESTADO IN FIDE_MODULO_TB.ID_ESTADO%TYPE
    ) IS
    BEGIN
        INSERT INTO FIDE_MODULO_TB(NOMBRE, DESCRIPCION, ID_ESTADO)
        VALUES (P_NOMBRE, P_DESCRIPCION, P_ID_ESTADO);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Registro insertado correctamente en FIDE_MODULO_TB.');
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: El módulo ' || P_NOMBRE || ' ya existe.');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en módulo.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_MODULO_INSERTAR_SP;

    PROCEDURE FIDE_MODULO_EDITAR_SP(
        P_ID_MODULO IN FIDE_MODULO_TB.ID_MODULO%TYPE,
        P_NOMBRE IN FIDE_MODULO_TB.NOMBRE%TYPE,
        P_DESCRIPCION IN FIDE_MODULO_TB.DESCRIPCION%TYPE,
        P_ID_ESTADO IN FIDE_MODULO_TB.ID_ESTADO%TYPE
    ) IS
    BEGIN
        UPDATE FIDE_MODULO_TB
        SET NOMBRE = P_NOMBRE,
            DESCRIPCION = P_DESCRIPCION,
            ID_ESTADO = P_ID_ESTADO
        WHERE ID_MODULO = P_ID_MODULO;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró el módulo con ID ' || P_ID_MODULO);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Módulo actualizado exitosamente.');
        END IF;
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: El nombre del módulo ya existe.');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en la actualización.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al actualizar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_MODULO_EDITAR_SP;

    PROCEDURE FIDE_MODULO_ELIMINAR_SP(
        P_ID_MODULO IN FIDE_MODULO_TB.ID_MODULO%TYPE
    ) IS
        V_ESTADO_INACTIVO CONSTANT NUMBER := 2;
    BEGIN
        UPDATE FIDE_MODULO_TB
        SET ID_ESTADO = V_ESTADO_INACTIVO
        WHERE ID_MODULO = P_ID_MODULO;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró el módulo con ID ' || P_ID_MODULO);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Módulo eliminado exitosamente.');
        END IF;
    EXCEPTION
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Valor incorrecto en la eliminación.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al eliminar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_MODULO_ELIMINAR_SP;

    -- IMPLEMENTACIÓN PARA FIDE_MOVIMIENTOS_AHORRO_TB

    PROCEDURE FIDE_MOVIMIENTOS_AHORRO_INSERTAR_SP(
        P_ID_AHORRO      IN FIDE_MOVIMIENTOS_AHORRO_TB.ID_AHORRO%TYPE,
        P_ID_TRANSACCION IN FIDE_MOVIMIENTOS_AHORRO_TB.ID_TRANSACCION%TYPE,
        P_MONTO          IN FIDE_MOVIMIENTOS_AHORRO_TB.MONTO%TYPE,
        P_FECHA_DEPOSITO IN FIDE_MOVIMIENTOS_AHORRO_TB.FECHA_DEPOSITO%TYPE,
        P_TIPO_MOVIMIENTO IN VARCHAR2  -- 'DEPOSITO' o 'RETIRO'
    ) IS
        V_SALDO_ACTUAL FIDE_CUENTAS_AHORRO_TB.SALDO_ACTUAL%TYPE;
        V_NUEVO_SALDO  FIDE_CUENTAS_AHORRO_TB.SALDO_ACTUAL%TYPE;
        V_ID_USUARIO_TRANSACCION FIDE_TRANSACCION_TB.ID_USUARIO%TYPE;
        V_ID_USUARIO_AHORRO      FIDE_CUENTAS_AHORRO_TB.ID_USUARIO%TYPE;
    BEGIN
    
        SELECT ID_USUARIO INTO V_ID_USUARIO_TRANSACCION
        FROM FIDE_TRANSACCION_TB
        WHERE ID_TRANSACCION = P_ID_TRANSACCION;
    
        SELECT ID_USUARIO INTO V_ID_USUARIO_AHORRO
        FROM FIDE_CUENTAS_AHORRO_TB
        WHERE ID_AHORRO = P_ID_AHORRO;
    
        IF V_ID_USUARIO_TRANSACCION != V_ID_USUARIO_AHORRO THEN
            RAISE_APPLICATION_ERROR(-20005,
                'La transacción no pertenece al usuario de la cuenta de ahorro');
        END IF;
    
        SELECT SALDO_ACTUAL INTO V_SALDO_ACTUAL
        FROM FIDE_CUENTAS_AHORRO_TB
        WHERE ID_AHORRO = P_ID_AHORRO;
    
        -- Si es retiro validamos que no deje saldo negativo
        IF P_TIPO_MOVIMIENTO = 'RETIRO' THEN
            IF P_MONTO > V_SALDO_ACTUAL THEN
                RAISE_APPLICATION_ERROR(-20001,
                    'El monto a retirar (' || P_MONTO ||
                    ') supera el saldo disponible (' || V_SALDO_ACTUAL || ')');
            END IF;
            V_NUEVO_SALDO := V_SALDO_ACTUAL - P_MONTO;
        ELSIF P_TIPO_MOVIMIENTO = 'DEPOSITO' THEN
            V_NUEVO_SALDO := V_SALDO_ACTUAL + P_MONTO;
        ELSE
            RAISE_APPLICATION_ERROR(-20003,
                'Tipo de movimiento inválido. Use DEPOSITO o RETIRO');
        END IF;
    
        -- Insetamos el movimiento de ahorro
        INSERT INTO FIDE_MOVIMIENTOS_AHORRO_TB(
            ID_AHORRO, ID_TRANSACCION, MONTO, FECHA_DEPOSITO
        ) VALUES (
            P_ID_AHORRO, P_ID_TRANSACCION, P_MONTO, P_FECHA_DEPOSITO
        );
    
        -- Actualizando el saldo de la cuenta
        UPDATE FIDE_CUENTAS_AHORRO_TB
        SET SALDO_ACTUAL = V_NUEVO_SALDO
        WHERE ID_AHORRO = P_ID_AHORRO;
    
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Movimiento registrado. Nuevo saldo: ' || V_NUEVO_SALDO);
    
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            ROLLBACK;
            RAISE_APPLICATION_ERROR(-20002, 'No existe la cuenta de ahorro con ID: ' || P_ID_AHORRO);
        WHEN DUP_VAL_ON_INDEX THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: El movimiento de ahorro ya existe.');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en movimiento de ahorro.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_MOVIMIENTOS_AHORRO_INSERTAR_SP;

    PROCEDURE FIDE_MOVIMIENTOS_AHORRO_EDITAR_SP(
        P_ID_MOVIMIENTO IN FIDE_MOVIMIENTOS_AHORRO_TB.ID_MOVIMIENTO%TYPE,
        P_ID_AHORRO IN FIDE_MOVIMIENTOS_AHORRO_TB.ID_AHORRO%TYPE,
        P_ID_TRANSACCION IN FIDE_MOVIMIENTOS_AHORRO_TB.ID_TRANSACCION%TYPE,
        P_MONTO IN FIDE_MOVIMIENTOS_AHORRO_TB.MONTO%TYPE,
        P_FECHA_DEPOSITO IN FIDE_MOVIMIENTOS_AHORRO_TB.FECHA_DEPOSITO%TYPE
    ) IS
    BEGIN
        UPDATE FIDE_MOVIMIENTOS_AHORRO_TB
        SET ID_AHORRO = P_ID_AHORRO,
            ID_TRANSACCION = P_ID_TRANSACCION,
            MONTO = P_MONTO,
            FECHA_DEPOSITO = P_FECHA_DEPOSITO
        WHERE ID_MOVIMIENTO = P_ID_MOVIMIENTO;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró el movimiento con ID ' || P_ID_MOVIMIENTO);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Movimiento de ahorro actualizado exitosamente.');
        END IF;
    EXCEPTION
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en la actualización.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al actualizar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_MOVIMIENTOS_AHORRO_EDITAR_SP;

    PROCEDURE FIDE_MOVIMIENTOS_AHORRO_ELIMINAR_SP(
        P_ID_MOVIMIENTO IN FIDE_MOVIMIENTOS_AHORRO_TB.ID_MOVIMIENTO%TYPE
    ) IS
    BEGIN
        DELETE FROM FIDE_MOVIMIENTOS_AHORRO_TB
        WHERE ID_MOVIMIENTO = P_ID_MOVIMIENTO;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró el movimiento con ID ' || P_ID_MOVIMIENTO);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Movimiento de ahorro eliminado físicamente.');
        END IF;
    EXCEPTION
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Valor incorrecto en la eliminación.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al eliminar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_MOVIMIENTOS_AHORRO_ELIMINAR_SP;

    -- IMPLEMENTACIÓN PARA FIDE_NUMERO_TELEFONO_TB

    PROCEDURE FIDE_NUMERO_TELEFONO_INSERTAR_SP(
        P_NUMERO_TELEFONO IN FIDE_NUMERO_TELEFONO_TB.NUMERO_TELEFONO%TYPE,
        P_ID_ESTADO IN FIDE_NUMERO_TELEFONO_TB.ID_ESTADO%TYPE
    ) IS
    BEGIN
        INSERT INTO FIDE_NUMERO_TELEFONO_TB(NUMERO_TELEFONO, ID_ESTADO)
        VALUES (P_NUMERO_TELEFONO, P_ID_ESTADO);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Registro insertado correctamente en FIDE_NUMERO_TELEFONO_TB.');
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: El número de teléfono ' || P_NUMERO_TELEFONO || ' ya existe.');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de número telefónico incorrecto o valor nulo.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_NUMERO_TELEFONO_INSERTAR_SP;

    PROCEDURE FIDE_NUMERO_TELEFONO_EDITAR_SP(
        P_ID_NUMERO IN FIDE_NUMERO_TELEFONO_TB.ID_NUMERO%TYPE,
        P_NUMERO_TELEFONO IN FIDE_NUMERO_TELEFONO_TB.NUMERO_TELEFONO%TYPE,
        P_ID_ESTADO IN FIDE_NUMERO_TELEFONO_TB.ID_ESTADO%TYPE
    ) IS
    BEGIN
        UPDATE FIDE_NUMERO_TELEFONO_TB
        SET NUMERO_TELEFONO = P_NUMERO_TELEFONO,
            ID_ESTADO = P_ID_ESTADO
        WHERE ID_NUMERO = P_ID_NUMERO;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró el número con ID ' || P_ID_NUMERO);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Número de teléfono actualizado exitosamente.');
        END IF;
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: El número de teléfono ya existe.');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en la actualización.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al actualizar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_NUMERO_TELEFONO_EDITAR_SP;

    PROCEDURE FIDE_NUMERO_TELEFONO_ELIMINAR_SP(
        P_ID_NUMERO IN FIDE_NUMERO_TELEFONO_TB.ID_NUMERO%TYPE
    ) IS
        V_ESTADO_INACTIVO CONSTANT NUMBER := 2;
    BEGIN
        UPDATE FIDE_NUMERO_TELEFONO_TB
        SET ID_ESTADO = V_ESTADO_INACTIVO
        WHERE ID_NUMERO = P_ID_NUMERO;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró el número con ID ' || P_ID_NUMERO);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Número de teléfono eliminado exitosamente.');
        END IF;
    EXCEPTION
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Valor incorrecto en la eliminación.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al eliminar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_NUMERO_TELEFONO_ELIMINAR_SP;

    -- IMPLEMENTACIÓN PARA FIDE_PAGOS_PRESTAMOS_TB

    PROCEDURE FIDE_PAGOS_PRESTAMOS_INSERTAR_SP(
        P_ID_TRANSACCION IN FIDE_PAGOS_PRESTAMOS_TB.ID_TRANSACCION%TYPE,
        P_ID_PRESTAMO    IN FIDE_PAGOS_PRESTAMOS_TB.ID_PRESTAMO%TYPE,
        P_MONTO_ABONADO  IN FIDE_PAGOS_PRESTAMOS_TB.MONTO_ABONADO%TYPE,
        P_FECHA_PAGO     IN FIDE_PAGOS_PRESTAMOS_TB.FECHA_PAGO%TYPE
    ) IS
        V_SALDO_PENDIENTE FIDE_PRESTAMO_TB.SALDO_PENDIENTE%TYPE;
        V_NUEVO_SALDO     FIDE_PRESTAMO_TB.SALDO_PENDIENTE%TYPE;
        V_ID_USUARIO_TRANSACCION FIDE_TRANSACCION_TB.ID_USUARIO%TYPE;
        V_ID_USUARIO_PRESTAMO    FIDE_PRESTAMO_TB.ID_USUARIO%TYPE;  
    BEGIN
    
            SELECT ID_USUARIO INTO V_ID_USUARIO_TRANSACCION
            FROM FIDE_TRANSACCION_TB
            WHERE ID_TRANSACCION = P_ID_TRANSACCION;
        
            SELECT ID_USUARIO INTO V_ID_USUARIO_PRESTAMO  -- ← correcto
            FROM FIDE_PRESTAMO_TB
            WHERE ID_PRESTAMO = P_ID_PRESTAMO;
        
            IF V_ID_USUARIO_TRANSACCION != V_ID_USUARIO_PRESTAMO THEN
                RAISE_APPLICATION_ERROR(-20005,
                    'La transacción no pertenece al usuario del préstamo');
            END IF;
    
        -- Obtener saldo pendiente del préstamo
        SELECT SALDO_PENDIENTE INTO V_SALDO_PENDIENTE
        FROM FIDE_PRESTAMO_TB
        WHERE ID_PRESTAMO = P_ID_PRESTAMO;
    
        -- Validar que el abono no supere el saldo pendiente
        IF P_MONTO_ABONADO > V_SALDO_PENDIENTE THEN
            RAISE_APPLICATION_ERROR(-20001,
                'El monto abonado (' || P_MONTO_ABONADO ||
                ') supera el saldo pendiente (' || V_SALDO_PENDIENTE || ')');
        END IF;
    
        INSERT INTO FIDE_PAGOS_PRESTAMOS_TB(
            ID_TRANSACCION, ID_PRESTAMO, MONTO_ABONADO, FECHA_PAGO
        ) VALUES (
            P_ID_TRANSACCION, P_ID_PRESTAMO, P_MONTO_ABONADO, P_FECHA_PAGO
        );
    
        -- Calcular nuevo saldo
        V_NUEVO_SALDO := V_SALDO_PENDIENTE - P_MONTO_ABONADO;
    
        -- Actualizar saldo pendiente del préstamo
        UPDATE FIDE_PRESTAMO_TB
        SET SALDO_PENDIENTE = V_NUEVO_SALDO,
            -- Si saldo llega a 0, cambiar estado a "Completado" (ID_ESTADO = 9)
            ID_ESTADO = CASE WHEN V_NUEVO_SALDO = 0 THEN 9 ELSE ID_ESTADO END
        WHERE ID_PRESTAMO = P_ID_PRESTAMO;
    
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Pago registrado correctamente. Saldo pendiente: ' || V_NUEVO_SALDO);
    
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            ROLLBACK;
            RAISE_APPLICATION_ERROR(-20002, 'No existe el préstamo con ID: ' || P_ID_PRESTAMO);
        WHEN DUP_VAL_ON_INDEX THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: El pago de préstamo ya existe.');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en pago de préstamo.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_PAGOS_PRESTAMOS_INSERTAR_SP;

    PROCEDURE FIDE_PAGOS_PRESTAMOS_EDITAR_SP(
        P_ID_PAGO IN FIDE_PAGOS_PRESTAMOS_TB.ID_PAGO%TYPE,
        P_ID_TRANSACCION IN FIDE_PAGOS_PRESTAMOS_TB.ID_TRANSACCION%TYPE,
        P_ID_PRESTAMO IN FIDE_PAGOS_PRESTAMOS_TB.ID_PRESTAMO%TYPE,
        P_MONTO_ABONADO IN FIDE_PAGOS_PRESTAMOS_TB.MONTO_ABONADO%TYPE,
        P_FECHA_PAGO IN FIDE_PAGOS_PRESTAMOS_TB.FECHA_PAGO%TYPE
    ) IS
    BEGIN
        UPDATE FIDE_PAGOS_PRESTAMOS_TB
        SET ID_TRANSACCION = P_ID_TRANSACCION,
            ID_PRESTAMO = P_ID_PRESTAMO,
            MONTO_ABONADO = P_MONTO_ABONADO,
            FECHA_PAGO = P_FECHA_PAGO
        WHERE ID_PAGO = P_ID_PAGO;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró el pago con ID ' || P_ID_PAGO);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Pago de préstamo actualizado exitosamente.');
        END IF;
    EXCEPTION
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en la actualización.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al actualizar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_PAGOS_PRESTAMOS_EDITAR_SP;

    PROCEDURE FIDE_PAGOS_PRESTAMOS_ELIMINAR_SP(
        P_ID_PAGO IN FIDE_PAGOS_PRESTAMOS_TB.ID_PAGO%TYPE
    ) IS
    BEGIN
        DELETE FROM FIDE_PAGOS_PRESTAMOS_TB
        WHERE ID_PAGO = P_ID_PAGO;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró el pago con ID ' || P_ID_PAGO);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Pago de préstamo eliminado físicamente.');
        END IF;
    EXCEPTION
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Valor incorrecto en la eliminación.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al eliminar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_PAGOS_PRESTAMOS_ELIMINAR_SP;

    -- IMPLEMENTACIÓN PARA FIDE_PRESTAMO_TB

    PROCEDURE FIDE_PRESTAMO_INSERTAR_SP(
        P_ID_USUARIO       IN FIDE_PRESTAMO_TB.ID_USUARIO%TYPE,
        P_MONTO_SOLICITADO IN FIDE_PRESTAMO_TB.MONTO_SOLICITADO%TYPE,
        P_FECHA_APROBACION IN FIDE_PRESTAMO_TB.FECHA_APROBACION%TYPE,
        P_SALDO_PENDIENTE  IN FIDE_PRESTAMO_TB.SALDO_PENDIENTE%TYPE,
        P_ID_ESTADO        IN FIDE_PRESTAMO_TB.ID_ESTADO%TYPE,
        P_TASA_INTERESES   IN FIDE_PRESTAMO_TB.TASA_INTERESES%TYPE,
        P_PLAZO_MESES      IN FIDE_PRESTAMO_TB.PLAZO_MESES%TYPE
    ) IS
        V_SALDO_ACTUAL FIDE_CUENTAS_AHORRO_TB.SALDO_ACTUAL%TYPE;
    BEGIN
        -- Obtener saldo disponible del usuario
        SELECT SALDO_ACTUAL INTO V_SALDO_ACTUAL
        FROM FIDE_CUENTAS_AHORRO_TB
        WHERE ID_USUARIO = P_ID_USUARIO
          AND ID_ESTADO = 1
          AND ROWNUM = 1;
        
        -- Validar que el monto no supere el saldo disponible
        IF P_MONTO_SOLICITADO > V_SALDO_ACTUAL THEN
            RAISE_APPLICATION_ERROR(-20001,
                'El monto solicitado (' || P_MONTO_SOLICITADO ||
                ') supera el saldo disponible (' || V_SALDO_ACTUAL || ')');
        END IF;
    
        INSERT INTO FIDE_PRESTAMO_TB(
            ID_USUARIO, MONTO_SOLICITADO, FECHA_APROBACION, SALDO_PENDIENTE,
            ID_ESTADO, TASA_INTERESES, PLAZO_MESES
        ) VALUES (
            P_ID_USUARIO, P_MONTO_SOLICITADO, P_FECHA_APROBACION, P_SALDO_PENDIENTE,
            P_ID_ESTADO, P_TASA_INTERESES, P_PLAZO_MESES
        );
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Registro insertado correctamente en FIDE_PRESTAMO_TB.');
    
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            ROLLBACK;
            RAISE_APPLICATION_ERROR(-20002, 'El usuario no tiene cuenta de ahorro activa');
        WHEN DUP_VAL_ON_INDEX THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: El préstamo ya existe.');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en préstamo.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_PRESTAMO_INSERTAR_SP;

    PROCEDURE FIDE_PRESTAMO_EDITAR_SP(
        P_ID_PRESTAMO      IN FIDE_PRESTAMO_TB.ID_PRESTAMO%TYPE,
        P_ID_USUARIO       IN FIDE_PRESTAMO_TB.ID_USUARIO%TYPE,
        P_MONTO_SOLICITADO IN FIDE_PRESTAMO_TB.MONTO_SOLICITADO%TYPE,
        P_FECHA_APROBACION IN FIDE_PRESTAMO_TB.FECHA_APROBACION%TYPE,
        P_SALDO_PENDIENTE  IN FIDE_PRESTAMO_TB.SALDO_PENDIENTE%TYPE,
        P_ID_ESTADO        IN FIDE_PRESTAMO_TB.ID_ESTADO%TYPE,
        P_TASA_INTERESES   IN FIDE_PRESTAMO_TB.TASA_INTERESES%TYPE,
        P_PLAZO_MESES      IN FIDE_PRESTAMO_TB.PLAZO_MESES%TYPE
    ) IS
        V_ESTADO_ACTUAL    FIDE_PRESTAMO_TB.ID_ESTADO%TYPE;
        V_SALDO_ACTUAL     FIDE_CUENTAS_AHORRO_TB.SALDO_ACTUAL%TYPE;
    BEGIN
        SELECT ID_ESTADO INTO V_ESTADO_ACTUAL
        FROM FIDE_PRESTAMO_TB
        WHERE ID_PRESTAMO = P_ID_PRESTAMO;
    
        -- No permitir editar préstamos ya pagados
        IF V_ESTADO_ACTUAL = 2 THEN  -- ajustar ID según tu tabla de estados
            RAISE_APPLICATION_ERROR(-20001,
                'No se puede editar un préstamo ya pagado');
        END IF;
    
        -- Si se modifica el monto, revalidar contra saldo disponible
        SELECT SALDO_ACTUAL INTO V_SALDO_ACTUAL
        FROM FIDE_CUENTAS_AHORRO_TB
        WHERE ID_USUARIO = P_ID_USUARIO
          AND ID_ESTADO = 1
          AND ROWNUM = 1;
    
        IF P_MONTO_SOLICITADO > V_SALDO_ACTUAL THEN
            RAISE_APPLICATION_ERROR(-20002,
                'El monto solicitado (' || P_MONTO_SOLICITADO ||
                ') supera el saldo disponible (' || V_SALDO_ACTUAL || ')');
        END IF;
    
        UPDATE FIDE_PRESTAMO_TB
        SET ID_USUARIO         = P_ID_USUARIO,
            MONTO_SOLICITADO   = P_MONTO_SOLICITADO,
            FECHA_APROBACION   = P_FECHA_APROBACION,
            SALDO_PENDIENTE    = P_SALDO_PENDIENTE,
            ID_ESTADO          = P_ID_ESTADO,
            TASA_INTERESES     = P_TASA_INTERESES,
            PLAZO_MESES        = P_PLAZO_MESES
        WHERE ID_PRESTAMO = P_ID_PRESTAMO;
    
        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró el préstamo con ID ' || P_ID_PRESTAMO);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Préstamo actualizado exitosamente.');
        END IF;
    
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            ROLLBACK;
            RAISE_APPLICATION_ERROR(-20003, 'No existe el préstamo o cuenta de ahorro');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en la actualización.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al actualizar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_PRESTAMO_EDITAR_SP;

    PROCEDURE FIDE_PRESTAMO_ELIMINAR_SP(
        P_ID_PRESTAMO IN FIDE_PRESTAMO_TB.ID_PRESTAMO%TYPE
    ) IS
        V_ESTADO_INACTIVO CONSTANT NUMBER := 2;
    BEGIN
        UPDATE FIDE_PRESTAMO_TB
        SET ID_ESTADO = V_ESTADO_INACTIVO
        WHERE ID_PRESTAMO = P_ID_PRESTAMO;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró el préstamo con ID ' || P_ID_PRESTAMO);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Préstamo eliminado exitosamente.');
        END IF;
    EXCEPTION
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Valor incorrecto en la eliminación.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al eliminar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_PRESTAMO_ELIMINAR_SP;

    -- IMPLEMENTACIÓN PARA FIDE_PUESTO_EMPRESA_ASOCIADO_TB

    PROCEDURE FIDE_PUESTO_EMPRESA_ASOCIADO_INSERTAR_SP(
        P_PUESTO_EMPRESA IN FIDE_PUESTO_EMPRESA_ASOCIADO_TB.PUESTO_EMPRESA%TYPE,
        P_ID_ESTADO IN FIDE_PUESTO_EMPRESA_ASOCIADO_TB.ID_ESTADO%TYPE
    ) IS
    BEGIN
        INSERT INTO FIDE_PUESTO_EMPRESA_ASOCIADO_TB(PUESTO_EMPRESA, ID_ESTADO)
        VALUES (P_PUESTO_EMPRESA, P_ID_ESTADO);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Registro insertado correctamente en FIDE_PUESTO_EMPRESA_ASOCIADO_TB.');
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: El puesto ' || P_PUESTO_EMPRESA || ' ya existe.');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en puesto de empresa.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_PUESTO_EMPRESA_ASOCIADO_INSERTAR_SP;

    PROCEDURE FIDE_PUESTO_EMPRESA_ASOCIADO_EDITAR_SP(
        P_ID_PUESTO_EMPRESA IN FIDE_PUESTO_EMPRESA_ASOCIADO_TB.ID_PUESTO_EMPRESA%TYPE,
        P_PUESTO_EMPRESA IN FIDE_PUESTO_EMPRESA_ASOCIADO_TB.PUESTO_EMPRESA%TYPE,
        P_ID_ESTADO IN FIDE_PUESTO_EMPRESA_ASOCIADO_TB.ID_ESTADO%TYPE
    ) IS
    BEGIN
        UPDATE FIDE_PUESTO_EMPRESA_ASOCIADO_TB
        SET PUESTO_EMPRESA = P_PUESTO_EMPRESA,
            ID_ESTADO = P_ID_ESTADO
        WHERE ID_PUESTO_EMPRESA = P_ID_PUESTO_EMPRESA;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró el puesto con ID ' || P_ID_PUESTO_EMPRESA);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Puesto de empresa actualizado exitosamente.');
        END IF;
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: El nombre del puesto ya existe.');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en la actualización.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al actualizar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_PUESTO_EMPRESA_ASOCIADO_EDITAR_SP;

    PROCEDURE FIDE_PUESTO_EMPRESA_ASOCIADO_ELIMINAR_SP(
        P_ID_PUESTO_EMPRESA IN FIDE_PUESTO_EMPRESA_ASOCIADO_TB.ID_PUESTO_EMPRESA%TYPE
    ) IS
        V_ESTADO_INACTIVO CONSTANT NUMBER := 2;
    BEGIN
        UPDATE FIDE_PUESTO_EMPRESA_ASOCIADO_TB
        SET ID_ESTADO = V_ESTADO_INACTIVO
        WHERE ID_PUESTO_EMPRESA = P_ID_PUESTO_EMPRESA;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró el puesto con ID ' || P_ID_PUESTO_EMPRESA);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Puesto de empresa eliminado exitosamente.');
        END IF;
    EXCEPTION
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Valor incorrecto en la eliminación.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al eliminar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_PUESTO_EMPRESA_ASOCIADO_ELIMINAR_SP;

    -- IMPLEMENTACIÓN PARA FIDE_REPORTES_TB

    PROCEDURE FIDE_REPORTES_INSERTAR_SP(
        P_ID_TIPO_REPORTE IN FIDE_REPORTES_TB.ID_TIPO_REPORTE%TYPE,
        P_FECHA_INICIO    IN FIDE_REPORTES_TB.FECHA_INICIO%TYPE,
        P_FECHA_FINAL     IN FIDE_REPORTES_TB.FECHA_FINAL%TYPE,
        P_ID_MODULO       IN FIDE_REPORTES_TB.ID_MODULO%TYPE,
        P_ID_ESTADO       IN FIDE_REPORTES_TB.ID_ESTADO%TYPE,
        P_ID_USUARIO      IN FIDE_REPORTES_TB.ID_USUARIO%TYPE
    ) IS
        V_TOTAL_REGISTROS NUMBER := 0;
        V_RESUMEN_MONTOS  NUMBER := 0;
    BEGIN
        CASE P_ID_MODULO
            WHEN 1 THEN -- Asociados (Aportes)
                SELECT COUNT(*), NVL(SUM(MONTO), 0)
                INTO V_TOTAL_REGISTROS, V_RESUMEN_MONTOS
                FROM FIDE_APORTE_TB
                WHERE FECHA_INICIO BETWEEN P_FECHA_INICIO AND P_FECHA_FINAL;
    
            WHEN 2 THEN -- Ahorros
                SELECT COUNT(*), NVL(SUM(MONTO_APORTE), 0)
                INTO V_TOTAL_REGISTROS, V_RESUMEN_MONTOS
                FROM FIDE_CUENTAS_AHORRO_TB
                WHERE FECHA_APERTURA BETWEEN P_FECHA_INICIO AND P_FECHA_FINAL;
    
            WHEN 3 THEN -- Préstamos
                SELECT COUNT(*), NVL(SUM(MONTO_SOLICITADO), 0)
                INTO V_TOTAL_REGISTROS, V_RESUMEN_MONTOS
                FROM FIDE_PRESTAMO_TB
                WHERE FECHA_APROBACION BETWEEN P_FECHA_INICIO AND P_FECHA_FINAL;
    
            WHEN 4 THEN -- Actividades (sin monto)
                SELECT COUNT(*), 0
                INTO V_TOTAL_REGISTROS, V_RESUMEN_MONTOS
                FROM FIDE_INSCRIPCIONES_ACTIVIDAD_TB
                WHERE FECHA_INSCRIPCION BETWEEN P_FECHA_INICIO AND P_FECHA_FINAL;
    
            ELSE
                RAISE_APPLICATION_ERROR(-20001, 'Módulo no válido para generación de reportes.');
        END CASE;
    
        INSERT INTO FIDE_REPORTES_TB(
            ID_TIPO_REPORTE, FECHA_INICIO, FECHA_FINAL, ID_MODULO,
            TOTAL_REGISTROS, RESUMEN_MONTOS, FECHA_GENERACION, ID_ESTADO, ID_USUARIO
        ) VALUES (
            P_ID_TIPO_REPORTE, P_FECHA_INICIO, P_FECHA_FINAL, P_ID_MODULO,
            V_TOTAL_REGISTROS, V_RESUMEN_MONTOS, SYSDATE, P_ID_ESTADO, P_ID_USUARIO
        );
        COMMIT;
    
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            ROLLBACK;
            RAISE_APPLICATION_ERROR(-20002, 'No se encontraron registros en el rango de fechas indicado.');
        WHEN OTHERS THEN
            ROLLBACK;
            RAISE_APPLICATION_ERROR(-20003, 'Error inesperado (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_REPORTES_INSERTAR_SP;

    PROCEDURE FIDE_REPORTES_EDITAR_SP(
        P_ID_REPORTE IN FIDE_REPORTES_TB.ID_REPORTE%TYPE,
        P_ID_TIPO_REPORTE IN FIDE_REPORTES_TB.ID_TIPO_REPORTE%TYPE,
        P_FECHA_INICIO IN FIDE_REPORTES_TB.FECHA_INICIO%TYPE,
        P_FECHA_FINAL IN FIDE_REPORTES_TB.FECHA_FINAL%TYPE,
        P_ID_MODULO IN FIDE_REPORTES_TB.ID_MODULO%TYPE,
        P_TOTAL_REGISTROS IN FIDE_REPORTES_TB.TOTAL_REGISTROS%TYPE,
        P_RESUMEN_MONTOS IN FIDE_REPORTES_TB.RESUMEN_MONTOS%TYPE,
        P_FECHA_GENERACION IN FIDE_REPORTES_TB.FECHA_GENERACION%TYPE,
        P_ID_ESTADO IN FIDE_REPORTES_TB.ID_ESTADO%TYPE,
        P_ID_USUARIO IN FIDE_REPORTES_TB.ID_USUARIO%TYPE
    ) IS
    BEGIN
        UPDATE FIDE_REPORTES_TB
        SET ID_TIPO_REPORTE = P_ID_TIPO_REPORTE,
            FECHA_INICIO = P_FECHA_INICIO,
            FECHA_FINAL = P_FECHA_FINAL,
            ID_MODULO = P_ID_MODULO,
            TOTAL_REGISTROS = P_TOTAL_REGISTROS,
            RESUMEN_MONTOS = P_RESUMEN_MONTOS,
            FECHA_GENERACION = P_FECHA_GENERACION,
            ID_ESTADO = P_ID_ESTADO,
            ID_USUARIO = P_ID_USUARIO
        WHERE ID_REPORTE = P_ID_REPORTE;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró el reporte con ID ' || P_ID_REPORTE);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Reporte actualizado exitosamente.');
        END IF;
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            ROLLBACK;
            RAISE_APPLICATION_ERROR(-20001, 'El reporte ya existe.');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            RAISE_APPLICATION_ERROR(-20002, 'Formato de dato incorrecto o valor nulo en reporte.');
        WHEN OTHERS THEN
            ROLLBACK;
            RAISE_APPLICATION_ERROR(-20003, 'Error inesperado (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_REPORTES_EDITAR_SP;

    PROCEDURE FIDE_REPORTES_ELIMINAR_SP(
        P_ID_REPORTE IN FIDE_REPORTES_TB.ID_REPORTE%TYPE
    ) IS
        V_ESTADO_INACTIVO CONSTANT NUMBER := 2;
    BEGIN
        UPDATE FIDE_REPORTES_TB
        SET ID_ESTADO = V_ESTADO_INACTIVO
        WHERE ID_REPORTE = P_ID_REPORTE;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró el reporte con ID ' || P_ID_REPORTE);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Reporte eliminado exitosamente.');
        END IF;
    EXCEPTION
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Valor incorrecto en la eliminación.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al eliminar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_REPORTES_ELIMINAR_SP;

    -- IMPLEMENTACIÓN PARA FIDE_RESULTADOS_REPORTE_TB

    PROCEDURE FIDE_RESULTADOS_REPORTE_INSERTAR_SP(
        P_ID_REPORTE IN FIDE_RESULTADOS_REPORTE_TB.ID_REPORTE%TYPE,
        P_METRICA_NOMBRE IN FIDE_RESULTADOS_REPORTE_TB.METRICA_NOMBRE%TYPE,
        P_METRICA_VALOR IN FIDE_RESULTADOS_REPORTE_TB.METRICA_VALOR%TYPE
    ) IS
    BEGIN
        INSERT INTO FIDE_RESULTADOS_REPORTE_TB(ID_REPORTE, METRICA_NOMBRE, METRICA_VALOR)
        VALUES (P_ID_REPORTE, P_METRICA_NOMBRE, P_METRICA_VALOR);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Registro insertado correctamente en FIDE_RESULTADOS_REPORTE_TB.');
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: El resultado de reporte ya existe.');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en resultado de reporte.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_RESULTADOS_REPORTE_INSERTAR_SP;

    PROCEDURE FIDE_RESULTADOS_REPORTE_EDITAR_SP(
        P_ID_RESULTADO IN FIDE_RESULTADOS_REPORTE_TB.ID_RESULTADO%TYPE,
        P_ID_REPORTE IN FIDE_RESULTADOS_REPORTE_TB.ID_REPORTE%TYPE,
        P_METRICA_NOMBRE IN FIDE_RESULTADOS_REPORTE_TB.METRICA_NOMBRE%TYPE,
        P_METRICA_VALOR IN FIDE_RESULTADOS_REPORTE_TB.METRICA_VALOR%TYPE
    ) IS
    BEGIN
        UPDATE FIDE_RESULTADOS_REPORTE_TB
        SET ID_REPORTE = P_ID_REPORTE,
            METRICA_NOMBRE = P_METRICA_NOMBRE,
            METRICA_VALOR = P_METRICA_VALOR
        WHERE ID_RESULTADO = P_ID_RESULTADO;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró el resultado con ID ' || P_ID_RESULTADO);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Resultado de reporte actualizado exitosamente.');
        END IF;
    EXCEPTION
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en la actualización.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al actualizar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_RESULTADOS_REPORTE_EDITAR_SP;

    PROCEDURE FIDE_RESULTADOS_REPORTE_ELIMINAR_SP(
        P_ID_RESULTADO IN FIDE_RESULTADOS_REPORTE_TB.ID_RESULTADO%TYPE
    ) IS
    BEGIN
        DELETE FROM FIDE_RESULTADOS_REPORTE_TB
        WHERE ID_RESULTADO = P_ID_RESULTADO;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró el resultado con ID ' || P_ID_RESULTADO);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Resultado de reporte eliminado físicamente.');
        END IF;
    EXCEPTION
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Valor incorrecto en la eliminación.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al eliminar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_RESULTADOS_REPORTE_ELIMINAR_SP;

    -- IMPLEMENTACIÓN PARA FIDE_ROL_TB

    PROCEDURE FIDE_ROL_INSERTAR_SP(
        P_NOMBRE_ROL IN FIDE_ROL_TB.NOMBRE_ROL%TYPE,
        P_ID_ESTADO IN FIDE_ROL_TB.ID_ESTADO%TYPE
    ) IS
    BEGIN
        INSERT INTO FIDE_ROL_TB(NOMBRE_ROL, ID_ESTADO)
        VALUES (P_NOMBRE_ROL, P_ID_ESTADO);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Registro insertado correctamente en FIDE_ROL_TB.');
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: El rol ' || P_NOMBRE_ROL || ' ya existe.');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en rol.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_ROL_INSERTAR_SP;

    PROCEDURE FIDE_ROL_EDITAR_SP(
        P_ID_ROL IN FIDE_ROL_TB.ID_ROL%TYPE,
        P_NOMBRE_ROL IN FIDE_ROL_TB.NOMBRE_ROL%TYPE,
        P_ID_ESTADO IN FIDE_ROL_TB.ID_ESTADO%TYPE
    ) IS
    BEGIN
        UPDATE FIDE_ROL_TB
        SET NOMBRE_ROL = P_NOMBRE_ROL,
            ID_ESTADO = P_ID_ESTADO
        WHERE ID_ROL = P_ID_ROL;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró el rol con ID ' || P_ID_ROL);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Rol actualizado exitosamente.');
        END IF;
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: El nombre del rol ya existe.');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en la actualización.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al actualizar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_ROL_EDITAR_SP;

    PROCEDURE FIDE_ROL_ELIMINAR_SP(
        P_ID_ROL IN FIDE_ROL_TB.ID_ROL%TYPE
    ) IS
        V_ESTADO_INACTIVO CONSTANT NUMBER := 2;
    BEGIN
        UPDATE FIDE_ROL_TB
        SET ID_ESTADO = V_ESTADO_INACTIVO
        WHERE ID_ROL = P_ID_ROL;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró el rol con ID ' || P_ID_ROL);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Rol eliminado exitosamente.');
        END IF;
    EXCEPTION
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Valor incorrecto en la eliminación.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al eliminar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_ROL_ELIMINAR_SP;

    -- IMPLEMENTACIÓN PARA FIDE_SERVICIO_TB

    PROCEDURE FIDE_SERVICIO_INSERTAR_SP(
        P_NOMBRE_SERVICIO IN FIDE_SERVICIO_TB.NOMBRE_SERVICIO%TYPE,
        P_DESCRIPCION IN FIDE_SERVICIO_TB.DESCRIPCION%TYPE,
        P_VALOR_ESTIMADO IN FIDE_SERVICIO_TB.VALOR_ESTIMADO%TYPE,
        P_STOCK IN FIDE_SERVICIO_TB.STOCK%TYPE,
        P_ID_CATEGORIA IN FIDE_SERVICIO_TB.ID_CATEGORIA%TYPE,
        P_ID_ESTADO IN FIDE_SERVICIO_TB.ID_ESTADO%TYPE
    ) IS
    BEGIN
        INSERT INTO FIDE_SERVICIO_TB(
            NOMBRE_SERVICIO, DESCRIPCION, VALOR_ESTIMADO, STOCK, ID_CATEGORIA, ID_ESTADO
        ) VALUES (
            P_NOMBRE_SERVICIO, P_DESCRIPCION, P_VALOR_ESTIMADO, P_STOCK, P_ID_CATEGORIA, P_ID_ESTADO
        );
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Registro insertado correctamente en FIDE_SERVICIO_TB.');
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: El servicio ' || P_NOMBRE_SERVICIO || ' ya existe.');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en servicio.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_SERVICIO_INSERTAR_SP;

    PROCEDURE FIDE_SERVICIO_EDITAR_SP(
        P_ID_SERVICIO IN FIDE_SERVICIO_TB.ID_SERVICIO%TYPE,
        P_NOMBRE_SERVICIO IN FIDE_SERVICIO_TB.NOMBRE_SERVICIO%TYPE,
        P_DESCRIPCION IN FIDE_SERVICIO_TB.DESCRIPCION%TYPE,
        P_VALOR_ESTIMADO IN FIDE_SERVICIO_TB.VALOR_ESTIMADO%TYPE,
        P_STOCK IN FIDE_SERVICIO_TB.STOCK%TYPE,
        P_ID_CATEGORIA IN FIDE_SERVICIO_TB.ID_CATEGORIA%TYPE,
        P_ID_ESTADO IN FIDE_SERVICIO_TB.ID_ESTADO%TYPE
    ) IS
    BEGIN
        UPDATE FIDE_SERVICIO_TB
        SET NOMBRE_SERVICIO = P_NOMBRE_SERVICIO,
            DESCRIPCION = P_DESCRIPCION,
            VALOR_ESTIMADO = P_VALOR_ESTIMADO,
            STOCK = P_STOCK,
            ID_CATEGORIA = P_ID_CATEGORIA,
            ID_ESTADO = P_ID_ESTADO
        WHERE ID_SERVICIO = P_ID_SERVICIO;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró el servicio con ID ' || P_ID_SERVICIO);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Servicio actualizado exitosamente.');
        END IF;
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: El nombre del servicio ya existe.');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en la actualización.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al actualizar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_SERVICIO_EDITAR_SP;

    PROCEDURE FIDE_SERVICIO_ELIMINAR_SP(
        P_ID_SERVICIO IN FIDE_SERVICIO_TB.ID_SERVICIO%TYPE
    ) IS
        V_ESTADO_INACTIVO CONSTANT NUMBER := 2;
    BEGIN
        UPDATE FIDE_SERVICIO_TB
        SET ID_ESTADO = V_ESTADO_INACTIVO
        WHERE ID_SERVICIO = P_ID_SERVICIO;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró el servicio con ID ' || P_ID_SERVICIO);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Servicio eliminado exitosamente.');
        END IF;
    EXCEPTION
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Valor incorrecto en la eliminación.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al eliminar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_SERVICIO_ELIMINAR_SP;

    -- IMPLEMENTACIÓN PARA FIDE_TIPO_AHORRO_TB

    PROCEDURE FIDE_TIPO_AHORRO_INSERTAR_SP(
        P_NOMBRE IN FIDE_TIPO_AHORRO_TB.NOMBRE%TYPE,
        P_DESCRIPCION IN FIDE_TIPO_AHORRO_TB.DESCRIPCION%TYPE,
        P_ID_ESTADO IN FIDE_TIPO_AHORRO_TB.ID_ESTADO%TYPE
    ) IS
    BEGIN
        INSERT INTO FIDE_TIPO_AHORRO_TB(NOMBRE, DESCRIPCION, ID_ESTADO)
        VALUES (P_NOMBRE, P_DESCRIPCION, P_ID_ESTADO);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Registro insertado correctamente en FIDE_TIPO_AHORRO_TB.');
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: El tipo de ahorro ' || P_NOMBRE || ' ya existe.');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en tipo de ahorro.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_TIPO_AHORRO_INSERTAR_SP;

    PROCEDURE FIDE_TIPO_AHORRO_EDITAR_SP(
        P_ID_TIPO_AHORRO IN FIDE_TIPO_AHORRO_TB.ID_TIPO_AHORRO%TYPE,
        P_NOMBRE IN FIDE_TIPO_AHORRO_TB.NOMBRE%TYPE,
        P_DESCRIPCION IN FIDE_TIPO_AHORRO_TB.DESCRIPCION%TYPE,
        P_ID_ESTADO IN FIDE_TIPO_AHORRO_TB.ID_ESTADO%TYPE
    ) IS
    BEGIN
        UPDATE FIDE_TIPO_AHORRO_TB
        SET NOMBRE = P_NOMBRE,
            DESCRIPCION = P_DESCRIPCION,
            ID_ESTADO = P_ID_ESTADO
        WHERE ID_TIPO_AHORRO = P_ID_TIPO_AHORRO;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró el tipo de ahorro con ID ' || P_ID_TIPO_AHORRO);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Tipo de ahorro actualizado exitosamente.');
        END IF;
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: El nombre del tipo de ahorro ya existe.');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en la actualización.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al actualizar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_TIPO_AHORRO_EDITAR_SP;

    PROCEDURE FIDE_TIPO_AHORRO_ELIMINAR_SP(
        P_ID_TIPO_AHORRO IN FIDE_TIPO_AHORRO_TB.ID_TIPO_AHORRO%TYPE
    ) IS
        V_ESTADO_INACTIVO CONSTANT NUMBER := 2;
    BEGIN
        UPDATE FIDE_TIPO_AHORRO_TB
        SET ID_ESTADO = V_ESTADO_INACTIVO
        WHERE ID_TIPO_AHORRO = P_ID_TIPO_AHORRO;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró el tipo de ahorro con ID ' || P_ID_TIPO_AHORRO);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Tipo de ahorro eliminado exitosamente.');
        END IF;
    EXCEPTION
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Valor incorrecto en la eliminación.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al eliminar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_TIPO_AHORRO_ELIMINAR_SP;

    -- IMPLEMENTACIÓN PARA FIDE_TIPO_REPORTE_TB

    PROCEDURE FIDE_TIPO_REPORTE_INSERTAR_SP(
        P_NOMBRE IN FIDE_TIPO_REPORTE_TB.NOMBRE%TYPE,
        P_ID_ESTADO IN FIDE_TIPO_REPORTE_TB.ID_ESTADO%TYPE
    ) IS
    BEGIN
        INSERT INTO FIDE_TIPO_REPORTE_TB(NOMBRE, ID_ESTADO)
        VALUES (P_NOMBRE, P_ID_ESTADO);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Registro insertado correctamente en FIDE_TIPO_REPORTE_TB.');
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: El tipo de reporte ' || P_NOMBRE || ' ya existe.');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en tipo de reporte.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_TIPO_REPORTE_INSERTAR_SP;

    PROCEDURE FIDE_TIPO_REPORTE_EDITAR_SP(
        P_ID_TIPO_REPORTE IN FIDE_TIPO_REPORTE_TB.ID_TIPO_REPORTE%TYPE,
        P_NOMBRE IN FIDE_TIPO_REPORTE_TB.NOMBRE%TYPE,
        P_ID_ESTADO IN FIDE_TIPO_REPORTE_TB.ID_ESTADO%TYPE
    ) IS
    BEGIN
        UPDATE FIDE_TIPO_REPORTE_TB
        SET NOMBRE = P_NOMBRE,
            ID_ESTADO = P_ID_ESTADO
        WHERE ID_TIPO_REPORTE = P_ID_TIPO_REPORTE;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró el tipo de reporte con ID ' || P_ID_TIPO_REPORTE);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Tipo de reporte actualizado exitosamente.');
        END IF;
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: El nombre del tipo de reporte ya existe.');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en la actualización.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al actualizar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_TIPO_REPORTE_EDITAR_SP;

    PROCEDURE FIDE_TIPO_REPORTE_ELIMINAR_SP(
        P_ID_TIPO_REPORTE IN FIDE_TIPO_REPORTE_TB.ID_TIPO_REPORTE%TYPE
    ) IS
        V_ESTADO_INACTIVO CONSTANT NUMBER := 2;
    BEGIN
        UPDATE FIDE_TIPO_REPORTE_TB
        SET ID_ESTADO = V_ESTADO_INACTIVO
        WHERE ID_TIPO_REPORTE = P_ID_TIPO_REPORTE;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró el tipo de reporte con ID ' || P_ID_TIPO_REPORTE);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Tipo de reporte eliminado exitosamente.');
        END IF;
    EXCEPTION
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Valor incorrecto en la eliminación.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al eliminar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_TIPO_REPORTE_ELIMINAR_SP;

    -- IMPLEMENTACIÓN PARA FIDE_TIPO_TRANSACCION_TB

    PROCEDURE FIDE_TIPO_TRANSACCION_INSERTAR_SP(
        P_NOMBRE IN FIDE_TIPO_TRANSACCION_TB.NOMBRE%TYPE,
        P_DESCRIPCION IN FIDE_TIPO_TRANSACCION_TB.DESCRIPCION%TYPE,
        P_ID_ESTADO IN FIDE_TIPO_TRANSACCION_TB.ID_ESTADO%TYPE
    ) IS
    BEGIN
        INSERT INTO FIDE_TIPO_TRANSACCION_TB(NOMBRE, DESCRIPCION, ID_ESTADO)
        VALUES (P_NOMBRE, P_DESCRIPCION, P_ID_ESTADO);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Registro insertado correctamente en FIDE_TIPO_TRANSACCION_TB.');
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: El tipo de transacción ' || P_NOMBRE || ' ya existe.');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en tipo de transacción.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_TIPO_TRANSACCION_INSERTAR_SP;

    PROCEDURE FIDE_TIPO_TRANSACCION_EDITAR_SP(
        P_ID_TIPO_TRANSACCION IN FIDE_TIPO_TRANSACCION_TB.ID_TIPO_TRANSACCION%TYPE,
        P_NOMBRE IN FIDE_TIPO_TRANSACCION_TB.NOMBRE%TYPE,
        P_DESCRIPCION IN FIDE_TIPO_TRANSACCION_TB.DESCRIPCION%TYPE,
        P_ID_ESTADO IN FIDE_TIPO_TRANSACCION_TB.ID_ESTADO%TYPE
    ) IS
    BEGIN
        UPDATE FIDE_TIPO_TRANSACCION_TB
        SET NOMBRE = P_NOMBRE,
            DESCRIPCION = P_DESCRIPCION,
            ID_ESTADO = P_ID_ESTADO
        WHERE ID_TIPO_TRANSACCION = P_ID_TIPO_TRANSACCION;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró el tipo de transacción con ID ' || P_ID_TIPO_TRANSACCION);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Tipo de transacción actualizado exitosamente.');
        END IF;
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: El nombre del tipo de transacción ya existe.');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en la actualización.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al actualizar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_TIPO_TRANSACCION_EDITAR_SP;

    PROCEDURE FIDE_TIPO_TRANSACCION_ELIMINAR_SP(
        P_ID_TIPO_TRANSACCION IN FIDE_TIPO_TRANSACCION_TB.ID_TIPO_TRANSACCION%TYPE
    ) IS
        V_ESTADO_INACTIVO CONSTANT NUMBER := 2;
    BEGIN
        UPDATE FIDE_TIPO_TRANSACCION_TB
        SET ID_ESTADO = V_ESTADO_INACTIVO
        WHERE ID_TIPO_TRANSACCION = P_ID_TIPO_TRANSACCION;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró el tipo de transacción con ID ' || P_ID_TIPO_TRANSACCION);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Tipo de transacción eliminado exitosamente.');
        END IF;
    EXCEPTION
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Valor incorrecto en la eliminación.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al eliminar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_TIPO_TRANSACCION_ELIMINAR_SP;

    -- IMPLEMENTACIÓN PARA FIDE_TRANSACCION_TB

    PROCEDURE FIDE_TRANSACCION_INSERTAR_SP(
        P_FECHA_TRANSACCION   IN FIDE_TRANSACCION_TB.FECHA_TRANSACCION%TYPE,
        P_ID_TIPO_TRANSACCION IN FIDE_TRANSACCION_TB.ID_TIPO_TRANSACCION%TYPE,
        P_MONTO_TOTAL         IN FIDE_TRANSACCION_TB.MONTO_TOTAL%TYPE,
        P_ID_USUARIO          IN FIDE_TRANSACCION_TB.ID_USUARIO%TYPE,
        P_ID_ESTADO           IN FIDE_TRANSACCION_TB.ID_ESTADO%TYPE,
        P_ID_TRANSACCION      OUT FIDE_TRANSACCION_TB.ID_TRANSACCION%TYPE
    ) IS
        V_SUMA_DETALLES NUMBER(10,2);
        V_ID_TRANSACCION FIDE_TRANSACCION_TB.ID_TRANSACCION%TYPE;
    BEGIN
        -- Insertar la transacción primero para obtener el ID
        INSERT INTO FIDE_TRANSACCION_TB(
            FECHA_TRANSACCION, ID_TIPO_TRANSACCION, MONTO_TOTAL, ID_USUARIO, ID_ESTADO
        ) VALUES (
            P_FECHA_TRANSACCION, P_ID_TIPO_TRANSACCION, P_MONTO_TOTAL, P_ID_USUARIO, P_ID_ESTADO
        ) RETURNING ID_TRANSACCION INTO V_ID_TRANSACCION;
    
        -- Verificar si ya existen detalles para esta transacción
        SELECT NVL(SUM(SUB_TOTAL), 0) INTO V_SUMA_DETALLES
        FROM FIDE_DETALLE_TRANSACCION_TB
        WHERE ID_TRANSACCION = V_ID_TRANSACCION;
    
        -- Validar que el monto total coincida con la suma de detalles
        IF V_SUMA_DETALLES > 0 AND P_MONTO_TOTAL != V_SUMA_DETALLES THEN
            RAISE_APPLICATION_ERROR(-20001,
                'El monto total (' || P_MONTO_TOTAL ||
                ') no coincide con la suma de detalles (' || V_SUMA_DETALLES || ')');
        END IF;
    
        P_ID_TRANSACCION := V_ID_TRANSACCION;
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Transacción insertada correctamente. ID: ' || V_ID_TRANSACCION);
    
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: La transacción ya existe.');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en transacción.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_TRANSACCION_INSERTAR_SP;

    PROCEDURE FIDE_TRANSACCION_EDITAR_SP(
        P_ID_TRANSACCION IN FIDE_TRANSACCION_TB.ID_TRANSACCION%TYPE,
        P_FECHA_TRANSACCION IN FIDE_TRANSACCION_TB.FECHA_TRANSACCION%TYPE,
        P_ID_TIPO_TRANSACCION IN FIDE_TRANSACCION_TB.ID_TIPO_TRANSACCION%TYPE,
        P_MONTO_TOTAL IN FIDE_TRANSACCION_TB.MONTO_TOTAL%TYPE,
        P_ID_USUARIO IN FIDE_TRANSACCION_TB.ID_USUARIO%TYPE,
        P_ID_ESTADO IN FIDE_TRANSACCION_TB.ID_ESTADO%TYPE
    ) IS
    BEGIN
        UPDATE FIDE_TRANSACCION_TB
        SET FECHA_TRANSACCION = P_FECHA_TRANSACCION,
            ID_TIPO_TRANSACCION = P_ID_TIPO_TRANSACCION,
            MONTO_TOTAL = P_MONTO_TOTAL,
            ID_USUARIO = P_ID_USUARIO,
            ID_ESTADO = P_ID_ESTADO
        WHERE ID_TRANSACCION = P_ID_TRANSACCION;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró la transacción con ID ' || P_ID_TRANSACCION);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Transacción actualizada exitosamente.');
        END IF;
    EXCEPTION
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en la actualización.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al actualizar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_TRANSACCION_EDITAR_SP;

    PROCEDURE FIDE_TRANSACCION_ELIMINAR_SP(
        P_ID_TRANSACCION IN FIDE_TRANSACCION_TB.ID_TRANSACCION%TYPE
    ) IS
        V_ESTADO_INACTIVO CONSTANT NUMBER := 2;
    BEGIN
        UPDATE FIDE_TRANSACCION_TB
        SET ID_ESTADO = V_ESTADO_INACTIVO
        WHERE ID_TRANSACCION = P_ID_TRANSACCION;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró la transacción con ID ' || P_ID_TRANSACCION);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Transacción eliminada exitosamente.');
        END IF;
    EXCEPTION
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Valor incorrecto en la eliminación.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al eliminar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_TRANSACCION_ELIMINAR_SP;

    -- IMPLEMENTACIÓN PARA FIDE_USUARIOS_ROLES_TB

    PROCEDURE FIDE_USUARIOS_ROLES_INSERTAR_SP(
        P_ID_USUARIO IN FIDE_USUARIOS_ROLES_TB.ID_USUARIO%TYPE,
        P_ID_ROL IN FIDE_USUARIOS_ROLES_TB.ID_ROL%TYPE
    ) IS
    BEGIN
        INSERT INTO FIDE_USUARIOS_ROLES_TB(ID_USUARIO, ID_ROL)
        VALUES (P_ID_USUARIO, P_ID_ROL);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Registro insertado correctamente en FIDE_USUARIOS_ROLES_TB.');
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: La combinación Usuario ' || P_ID_USUARIO || ' - Rol ' || P_ID_ROL || ' ya existe.');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en asignación de rol.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_USUARIOS_ROLES_INSERTAR_SP;

    PROCEDURE FIDE_USUARIOS_ROLES_EDITAR_SP(
        P_ID_USUARIO_OLD IN FIDE_USUARIOS_ROLES_TB.ID_USUARIO%TYPE,
        P_ID_ROL_OLD IN FIDE_USUARIOS_ROLES_TB.ID_ROL%TYPE,
        P_ID_USUARIO_NEW IN FIDE_USUARIOS_ROLES_TB.ID_USUARIO%TYPE,
        P_ID_ROL_NEW IN FIDE_USUARIOS_ROLES_TB.ID_ROL%TYPE
    ) IS
    BEGIN
        UPDATE FIDE_USUARIOS_ROLES_TB
        SET ID_USUARIO = P_ID_USUARIO_NEW,
            ID_ROL = P_ID_ROL_NEW
        WHERE ID_USUARIO = P_ID_USUARIO_OLD 
          AND ID_ROL = P_ID_ROL_OLD;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró la asignación Usuario ' || P_ID_USUARIO_OLD || ' - Rol ' || P_ID_ROL_OLD);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Asignación de rol actualizada exitosamente.');
        END IF;
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: La nueva combinación ya existe.');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en la actualización.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al actualizar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_USUARIOS_ROLES_EDITAR_SP;

    PROCEDURE FIDE_USUARIOS_ROLES_ELIMINAR_SP(
        P_ID_USUARIO IN FIDE_USUARIOS_ROLES_TB.ID_USUARIO%TYPE,
        P_ID_ROL IN FIDE_USUARIOS_ROLES_TB.ID_ROL%TYPE
    ) IS
    BEGIN
        DELETE FROM FIDE_USUARIOS_ROLES_TB
        WHERE ID_USUARIO = P_ID_USUARIO 
          AND ID_ROL = P_ID_ROL;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró la asignación Usuario ' || P_ID_USUARIO || ' - Rol ' || P_ID_ROL);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Asignación de rol eliminada físicamente.');
        END IF;
    EXCEPTION
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Valor incorrecto en la eliminación.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al eliminar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_USUARIOS_ROLES_ELIMINAR_SP;

    -- IMPLEMENTACIÓN PARA FIDE_DETALLE_TRANSACCION_TB

   PROCEDURE FIDE_DETALLE_TRANSACCION_INSERTAR_SP(
    P_ID_TRANSACCION IN FIDE_DETALLE_TRANSACCION_TB.ID_TRANSACCION%TYPE,
    P_CONCEPTO       IN FIDE_DETALLE_TRANSACCION_TB.CONCEPTO%TYPE,
    P_SUB_TOTAL      IN FIDE_DETALLE_TRANSACCION_TB.SUB_TOTAL%TYPE
) IS
    V_TRANSACCION_EXISTS NUMBER;
BEGIN
    SELECT COUNT(*) INTO V_TRANSACCION_EXISTS
    FROM FIDE_TRANSACCION_TB
    WHERE ID_TRANSACCION = P_ID_TRANSACCION;

    IF V_TRANSACCION_EXISTS = 0 THEN
        RAISE_APPLICATION_ERROR(-20001,
            'No existe la transacción con ID: ' || P_ID_TRANSACCION);
    END IF;

    INSERT INTO FIDE_DETALLE_TRANSACCION_TB(ID_TRANSACCION, CONCEPTO, SUB_TOTAL)
        VALUES (P_ID_TRANSACCION, P_CONCEPTO, P_SUB_TOTAL);
    
        -- Actualizar MONTO_TOTAL de la transacción con la suma de sus detalles
        UPDATE FIDE_TRANSACCION_TB
        SET MONTO_TOTAL = (
            SELECT SUM(SUB_TOTAL)
            FROM FIDE_DETALLE_TRANSACCION_TB
            WHERE ID_TRANSACCION = P_ID_TRANSACCION
        ),
        ID_ESTADO = 9
        WHERE ID_TRANSACCION = P_ID_TRANSACCION;
    
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Detalle insertado y monto total actualizado correctamente.');
    
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: El detalle de transacción ya existe.');
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en detalle de transacción.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_DETALLE_TRANSACCION_INSERTAR_SP;

    PROCEDURE FIDE_DETALLE_TRANSACCION_EDITAR_SP(
        P_ID_DETALLE IN FIDE_DETALLE_TRANSACCION_TB.ID_DETALLE%TYPE,
        P_ID_TRANSACCION IN FIDE_DETALLE_TRANSACCION_TB.ID_TRANSACCION%TYPE,
        P_CONCEPTO IN FIDE_DETALLE_TRANSACCION_TB.CONCEPTO%TYPE,
        P_SUB_TOTAL IN FIDE_DETALLE_TRANSACCION_TB.SUB_TOTAL%TYPE
    ) IS
    BEGIN
        UPDATE FIDE_DETALLE_TRANSACCION_TB
        SET ID_TRANSACCION = P_ID_TRANSACCION,
            CONCEPTO = P_CONCEPTO,
            SUB_TOTAL = P_SUB_TOTAL
        WHERE ID_DETALLE = P_ID_DETALLE;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró el detalle con ID ' || P_ID_DETALLE);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Detalle de transacción actualizado exitosamente.');
        END IF;
    EXCEPTION
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Formato de dato incorrecto o valor nulo en la actualización.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al actualizar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_DETALLE_TRANSACCION_EDITAR_SP;

    PROCEDURE FIDE_DETALLE_TRANSACCION_ELIMINAR_SP(
        P_ID_DETALLE IN FIDE_DETALLE_TRANSACCION_TB.ID_DETALLE%TYPE
    ) IS
    BEGIN
        DELETE FROM FIDE_DETALLE_TRANSACCION_TB
        WHERE ID_DETALLE = P_ID_DETALLE;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Advertencia: No se encontró el detalle con ID ' || P_ID_DETALLE);
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Detalle de transacción eliminado físicamente.');
        END IF;
    EXCEPTION
        WHEN VALUE_ERROR THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Valor incorrecto en la eliminación.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error inesperado al eliminar (' || SQLCODE || '): ' || SQLERRM);
    END FIDE_DETALLE_TRANSACCION_ELIMINAR_SP;

    PROCEDURE FIDE_GENERAR_REPORTE_SP(
            P_FECHA_INICIO IN DATE,
            P_FECHA_FINAL IN DATE,
            P_CURSOR OUT SYS_REFCURSOR
        ) AS
        BEGIN
            OPEN P_CURSOR FOR
            SELECT r.ID_REPORTE as ID_REPORTE,
               t.NOMBRE as NOMBRE_TIPO_REPORTE,
               r.FECHA_INICIO as FECHA_INICIO,
               r.FECHA_FINAL as FECHA_FINAL,
               m.NOMBRE as NOMBRE_MODULO_REPORTE,
               r.TOTAL_REGISTROS as TOTAL_REGISTROS,
               r.RESUMEN_MONTOS as RESUMEN_MONTOS,
               r.FECHA_GENERACION as FECHA_GENERACION,
               e.NOMBRE as NOMBRE_ESTADO,
               u.NOMBRE as NOMBRE_USUARIO,
               u.APELLIDO_PATERNO as APELLIDO_PATERNO
                    FROM FIDE_REPORTES_TB r
                    JOIN FIDE_TIPO_REPORTE_TB t ON r.ID_TIPO_REPORTE = t.ID_TIPO_REPORTE
                    JOIN FIDE_MODULO_TB m ON r.ID_MODULO = m.ID_MODULO
                    JOIN FIDE_ESTADO_TB e ON r.ID_ESTADO = e.ID_ESTADO
                    JOIN FIDE_USUARIO_TB u ON r.ID_USUARIO = u.ID_USUARIO
                    WHERE r.FECHA_INICIO >= P_FECHA_INICIO
                      AND r.FECHA_FINAL <= P_FECHA_FINAL
            ORDER BY r.FECHA_GENERACION DESC;
    END FIDE_GENERAR_REPORTE_SP;


    FUNCTION FIDE_REPORTE_NUM_FN RETURN NUMBER AS
        V_FECHA VARCHAR2(10);
        V_VALOR NUMBER;
    BEGIN
        V_FECHA := TO_CHAR(SYSDATE, 'DDMMYYYY');
        V_VALOR := TO_NUMBER(V_FECHA || LPAD(FIDE_REPORTES_SEQ.NEXTVAL, 7, 0));
        RETURN V_VALOR;
    END FIDE_REPORTE_NUM_FN;

    -- 1. Función Listar Usuarios

    FUNCTION FIDE_LISTAR_USUARIOS_FN RETURN SYS_REFCURSOR IS
        V_CURSOR SYS_REFCURSOR;
    BEGIN
        OPEN V_CURSOR FOR
            SELECT ID_USUARIO,
                   NOMBRE,
                   APELLIDO_PATERNO,
                   APELLIDO_MATERNO
            FROM FIDE_USUARIO_TB;
        RETURN V_CURSOR;
    EXCEPTION
        WHEN OTHERS THEN
            RETURN NULL;
    END FIDE_LISTAR_USUARIOS_FN;

    -- 2. Función Suma Total De Montos De Ahorro

    FUNCTION FIDE_SUMA_TOTAL_AHORROS_FN RETURN NUMBER IS
        CURSOR CURSOR_AHORROS IS
            SELECT SALDO_ACTUAL
            FROM FIDE_CUENTAS_AHORRO_TB;
        V_SUMA_TOTAL NUMBER := 0;
    BEGIN
        FOR AHORRO IN CURSOR_AHORROS LOOP
            V_SUMA_TOTAL := V_SUMA_TOTAL + AHORRO.SALDO_ACTUAL;
        END LOOP;
        DBMS_OUTPUT.PUT_LINE('TOTAL DE AHORROS DEL SISTEMA: ' || V_SUMA_TOTAL);
        RETURN V_SUMA_TOTAL;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            DBMS_OUTPUT.PUT_LINE('NO SE ENCONTRARON DATOS');
            RETURN 0;
        WHEN TOO_MANY_ROWS THEN
            DBMS_OUTPUT.PUT_LINE('DATOS DUPLICADOS');
            RETURN -1;
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('ERROR: ' || SQLERRM);
            RETURN -1;
    END FIDE_SUMA_TOTAL_AHORROS_FN;

    -- 3. Función Ordenar Usuarios Alfabéticamente

    FUNCTION FIDE_ORDENAR_USUARIOS_ALFABETICO_FN RETURN SYS_REFCURSOR IS
        V_CURSOR SYS_REFCURSOR;
    BEGIN
        OPEN V_CURSOR FOR
            SELECT NOMBRE,
                   APELLIDO_PATERNO,
                   APELLIDO_MATERNO
            FROM FIDE_USUARIO_TB
            ORDER BY NOMBRE ASC;--,
                     --APELLIDO_PATERNO ASC,
                     --APELLIDO_MATERNO ASC;
        RETURN V_CURSOR;
    EXCEPTION
        WHEN OTHERS THEN
            RETURN NULL;
    END FIDE_ORDENAR_USUARIOS_ALFABETICO_FN;

    -- 4. Función Ordenar Usuarios Por Fecha De Afiliación

   FUNCTION FIDE_ORDENAR_USUARIOS_FECHA_FN RETURN SYS_REFCURSOR IS
        V_CURSOR SYS_REFCURSOR;
    BEGIN
        OPEN V_CURSOR FOR
            SELECT U.NOMBRE,
                   U.APELLIDO_PATERNO,
                   D.FECHA_AFILIACION
            FROM FIDE_USUARIO_TB U
            JOIN FIDE_DATOS_ASOCIADOS_TB D
            ON U.ID_DATOS_ASOCIADOS = D.ID_DATOS_ASOCIADOS
            ORDER BY D.FECHA_AFILIACION ASC;
        RETURN V_CURSOR;
    EXCEPTION
        WHEN OTHERS THEN
            RETURN NULL;
    END FIDE_ORDENAR_USUARIOS_FECHA_FN;

    -- 5. Función Top 10 Usuarios Con Más Ahorros

   FUNCTION FIDE_TOP10_MAS_AHORROS_FN RETURN SYS_REFCURSOR IS
        V_CURSOR SYS_REFCURSOR;
    BEGIN
        OPEN V_CURSOR FOR
            SELECT * FROM (
                SELECT U.NOMBRE,
                       U.APELLIDO_PATERNO,
                       SUM(A.SALDO_ACTUAL) AS TOTAL_AHORRO
                FROM FIDE_USUARIO_TB U
                JOIN FIDE_CUENTAS_AHORRO_TB A
                ON U.ID_USUARIO = A.ID_USUARIO
                GROUP BY U.NOMBRE, U.APELLIDO_PATERNO
                ORDER BY TOTAL_AHORRO DESC
            ) WHERE ROWNUM <= 10;
        RETURN V_CURSOR;
    EXCEPTION
        WHEN OTHERS THEN
            RETURN NULL;
    END FIDE_TOP10_MAS_AHORROS_FN;

    -- 6. Función Top 10 Usuarios Con Menos Ahorros

   FUNCTION FIDE_TOP10_MENOS_AHORROS_FN RETURN SYS_REFCURSOR IS
        V_CURSOR SYS_REFCURSOR;
    BEGIN
        OPEN V_CURSOR FOR
            SELECT * FROM (
                SELECT U.NOMBRE,
                       U.APELLIDO_PATERNO,
                       NVL(SUM(A.SALDO_ACTUAL), 0) AS TOTAL_AHORRO
                FROM FIDE_USUARIO_TB U
                LEFT JOIN FIDE_CUENTAS_AHORRO_TB A
                ON U.ID_USUARIO = A.ID_USUARIO
                GROUP BY U.NOMBRE, U.APELLIDO_PATERNO
                ORDER BY TOTAL_AHORRO ASC
            ) WHERE ROWNUM <= 10;
        RETURN V_CURSOR;
    EXCEPTION
        WHEN OTHERS THEN
            RETURN NULL;
    END FIDE_TOP10_MENOS_AHORROS_FN;

    -- =============================================
    -- 7. Función Buscar Usuarios Por Nombre
    -- =============================================
     FUNCTION FIDE_BUSCAR_USUARIOS_NOMBRE_FN(
        P_NOMBRE_BUSCAR VARCHAR2
    ) RETURN SYS_REFCURSOR IS
        V_CURSOR SYS_REFCURSOR;
    BEGIN
        OPEN V_CURSOR FOR
            SELECT ID_USUARIO,
                   NOMBRE,
                   APELLIDO_PATERNO,
                   APELLIDO_MATERNO,
                   NOMBRE_USUARIO
            FROM FIDE_USUARIO_TB
            WHERE UPPER(NOMBRE)           LIKE '%' || UPPER(P_NOMBRE_BUSCAR) || '%'
               OR UPPER(APELLIDO_PATERNO) LIKE '%' || UPPER(P_NOMBRE_BUSCAR) || '%'
               OR UPPER(APELLIDO_MATERNO) LIKE '%' || UPPER(P_NOMBRE_BUSCAR) || '%'
            ORDER BY NOMBRE, APELLIDO_PATERNO;
        RETURN V_CURSOR;
    EXCEPTION
        WHEN OTHERS THEN
            RETURN NULL;
    END FIDE_BUSCAR_USUARIOS_NOMBRE_FN;
    
    FUNCTION FIDE_BUSCAR_NUMERO_TELEFONO_FN(
        P_NUMERO_TELEFONO_BUSCAR VARCHAR2
    ) RETURN SYS_REFCURSOR IS
        V_CURSOR SYS_REFCURSOR;
    BEGIN
        OPEN V_CURSOR FOR
            SELECT ID_NUMERO,
                   NUMERO_TELEFONO
            FROM FIDE_NUMERO_TELEFONO_TB
            WHERE NUMERO_TELEFONO = P_NUMERO_TELEFONO_BUSCAR 
            ORDER BY NUMERO_TELEFONO;
            RETURN V_CURSOR;
    EXCEPTION
        WHEN OTHERS THEN
            RETURN NULL;
    END FIDE_BUSCAR_NUMERO_TELEFONO_FN;
    
    FUNCTION FIDE_BUSCAR_CORREO_FN(
        P_CORREO_BUSCAR VARCHAR2
    ) RETURN SYS_REFCURSOR IS
        V_CURSOR SYS_REFCURSOR;
    BEGIN
        OPEN V_CURSOR FOR
            SELECT ID_CORREO,
                   CORREO_ELECTRONICO
            FROM FIDE_CORREO_ELECTRONICO_TB
            WHERE CORREO_ELECTRONICO = P_CORREO_BUSCAR
            ORDER BY CORREO_ELECTRONICO;
            RETURN V_CURSOR;
    EXCEPTION 
        WHEN OTHERS THEN 
            RETURN NULL;
    END FIDE_BUSCAR_CORREO_FN;
    
    FUNCTION FIDE_BUSCAR_DIRECCION_FN(
        P_DIRECCION_BUSCAR VARCHAR2
    ) RETURN SYS_REFCURSOR IS
        V_CURSOR SYS_REFCURSOR;
    BEGIN
        OPEN V_CURSOR FOR
            SELECT ID_DIRECCION,
                   PROVINCIA,
                   CANTON,
                   DISTRITO
            FROM FIDE_DIRECCION_TB
            WHERE PROVINCIA = P_DIRECCION_BUSCAR
            ORDER BY PROVINCIA;
            RETURN V_CURSOR;
    EXCEPTION
        WHEN OTHERS THEN
            RETURN NULL;
    END FIDE_BUSCAR_DIRECCION_FN;
    
    FUNCTION FIDE_BUSCAR_PUESTO_EMPRESA_ASOCIADO_FN(
        P_PUESTO_EMPRESA_ASOCIADO_BUSCAR VARCHAR2
    ) RETURN SYS_REFCURSOR IS
        V_CURSOR SYS_REFCURSOR;
    BEGIN
        OPEN V_CURSOR FOR
            SELECT ID_PUESTO_EMPRESA,
                   PUESTO_EMPRESA
            FROM FIDE_PUESTO_EMPRESA_ASOCIADO_TB
            WHERE PUESTO_EMPRESA = P_PUESTO_EMPRESA_ASOCIADO_BUSCAR
            ORDER BY PUESTO_EMPRESA;
            RETURN V_CURSOR;
    EXCEPTION
        WHEN OTHERS THEN
            RETURN NULL;
    END FIDE_BUSCAR_PUESTO_EMPRESA_ASOCIADO_FN;
    
    FUNCTION FIDE_BUSCAR_ACTIVIDAD_FN(
        P_NOMBRE_BUSCAR VARCHAR2
    ) RETURN SYS_REFCURSOR IS
        V_CURSOR SYS_REFCURSOR;
    BEGIN
        OPEN V_CURSOR FOR
            SELECT ID_ACTIVIDAD,
                   NOMBRE,
                   DESCRIPCION,
                   FECHA_EVENTO,
                   CUPO_TOTAL
            FROM FIDE_ACTIVIDAD_TB
            WHERE NOMBRE = P_NOMBRE_BUSCAR
            ORDER BY NOMBRE;
            RETURN V_CURSOR;
    EXCEPTION
        WHEN OTHERS THEN
            RETURN NULL;
    END FIDE_BUSCAR_ACTIVIDAD_FN;
    
    FUNCTION FIDE_BUSCAR_BENEFICIO_FN(
        P_NOMBRE_BENEFICIO_BUSCAR VARCHAR2
    ) RETURN SYS_REFCURSOR IS
        V_CURSOR SYS_REFCURSOR;
    BEGIN
        OPEN V_CURSOR FOR
            SELECT ID_BENEFICIO,
                   NOMBRE_BENEFICIO,
                   DESCRIPCION
            FROM FIDE_BENEFICIO_TB
            WHERE NOMBRE_BENEFICIO = P_NOMBRE_BENEFICIO_BUSCAR
            ORDER BY NOMBRE_BENEFICIO;
            RETURN V_CURSOR;
    EXCEPTION
        WHEN OTHERS THEN
            RETURN NULL;
    END FIDE_BUSCAR_BENEFICIO_FN;
    
    FUNCTION FIDE_BUSCAR_SERVICIO_FN(
        P_NOMBRE_SERVICIO_BUSCAR VARCHAR2
    ) RETURN SYS_REFCURSOR IS
        V_CURSOR SYS_REFCURSOR;
    BEGIN
        OPEN V_CURSOR FOR
            SELECT ID_SERVICIO,
                   NOMBRE_SERVICIO,
                   DESCRIPCION,
                   VALOR_ESTIMADO,
                   STOCK
            FROM FIDE_SERVICIO_TB
            WHERE NOMBRE_SERVICIO = P_NOMBRE_SERVICIO_BUSCAR
            ORDER BY NOMBRE_SERVICIO;
            RETURN V_CURSOR;
    EXCEPTION
        WHEN OTHERS THEN
            RETURN NULL;
    END FIDE_BUSCAR_SERVICIO_FN;
    
    FUNCTION FIDE_BUSCAR_CATEGORIA_FN(
        P_NOMBRE_CATEGORIA_BUSCAR VARCHAR2
    ) RETURN SYS_REFCURSOR IS
        V_CURSOR SYS_REFCURSOR;
    BEGIN
        OPEN V_CURSOR FOR
            SELECT ID_CATEGORIA,
                   NOMBRE,
                   DESCRIPCION
            FROM FIDE_CATEGORIA_TB
            WHERE NOMBRE = P_NOMBRE_CATEGORIA_BUSCAR
            ORDER BY NOMBRE;
            RETURN V_CURSOR;
    EXCEPTION
        WHEN OTHERS THEN
            RETURN NULL;
    END FIDE_BUSCAR_CATEGORIA_FN;
        
    FUNCTION FIDE_BUSCAR_LUGAR_EVENTO_FN(
        P_NOMBRE_LUGAR_EVENTO_BUSCAR VARCHAR2
    ) RETURN SYS_REFCURSOR IS
        V_CURSOR SYS_REFCURSOR;
    BEGIN
        OPEN V_CURSOR FOR
            SELECT ID_LUGAR_EVENTO,
                   NOMBRE_LUGAR
            FROM FIDE_LUGAR_EVENTO_TB
            WHERE NOMBRE_LUGAR = P_NOMBRE_LUGAR_EVENTO_BUSCAR
            ORDER BY NOMBRE_LUGAR;
            RETURN V_CURSOR;
    EXCEPTION
        WHEN OTHERS THEN
            RETURN NULL;
    END FIDE_BUSCAR_LUGAR_EVENTO_FN;
    
    FUNCTION FIDE_BUSCAR_TIPO_AHORRO_FN(
        P_NOMBRE_TIPO_AHORRO_BUSCAR VARCHAR2
    ) RETURN SYS_REFCURSOR IS
        V_CURSOR SYS_REFCURSOR;
    BEGIN
        OPEN V_CURSOR FOR
            SELECT ID_TIPO_AHORRO,
                   NOMBRE,
                   DESCRIPCION
            FROM FIDE_TIPO_AHORRO_TB
            WHERE NOMBRE = P_NOMBRE_TIPO_AHORRO_BUSCAR
            ORDER BY NOMBRE;
            RETURN V_CURSOR;
    EXCEPTION
        WHEN OTHERS THEN
            RETURN NULL;
    END FIDE_BUSCAR_TIPO_AHORRO_FN;
    
    FUNCTION FIDE_BUSCAR_TIPO_REPORTE_FN(
        P_NOMBRE_TIPO_REPORTE_BUSCAR VARCHAR2
    ) RETURN SYS_REFCURSOR IS
        V_CURSOR SYS_REFCURSOR;
    BEGIN
        OPEN V_CURSOR FOR
            SELECT ID_TIPO_REPORTE,
                   NOMBRE
            FROM FIDE_TIPO_REPORTE_TB
            WHERE NOMBRE = P_NOMBRE_TIPO_REPORTE_BUSCAR
            ORDER BY NOMBRE;
            RETURN V_CURSOR;
    EXCEPTION
        WHEN OTHERS THEN
            RETURN NULL;
    END FIDE_BUSCAR_TIPO_REPORTE_FN;
                    
    FUNCTION FIDE_BUSCAR_MODULO_REPORTE_FN(
        P_NOMBRE_MODULO_REPORTE_BUSCAR VARCHAR2
    ) RETURN SYS_REFCURSOR IS
        V_CURSOR SYS_REFCURSOR;
    BEGIN
        OPEN V_CURSOR FOR
            SELECT ID_MODULO,
                   NOMBRE,
                   DESCRIPCION
            FROM FIDE_MODULO_TB
            WHERE NOMBRE = P_NOMBRE_MODULO_REPORTE_BUSCAR
            ORDER BY NOMBRE;
            RETURN V_CURSOR;
    EXCEPTION
        WHEN OTHERS THEN
            RETURN NULL;
    END FIDE_BUSCAR_MODULO_REPORTE_FN;
                    
    FUNCTION FIDE_BUSCAR_TIPO_TRANSACCION_FN(
        P_NOMBRE_TIPO_TRANSACCION_BUSCAR VARCHAR2
    ) RETURN SYS_REFCURSOR IS
        V_CURSOR SYS_REFCURSOR;
    BEGIN
        OPEN V_CURSOR FOR
            SELECT ID_TIPO_TRANSACCION,
                   NOMBRE,
                   DESCRIPCION
            FROM FIDE_TIPO_TRANSACCION_TB
            WHERE NOMBRE = P_NOMBRE_TIPO_TRANSACCION_BUSCAR
            ORDER BY NOMBRE;
            RETURN V_CURSOR;
    EXCEPTION 
        WHEN OTHERS THEN
            RETURN NULL;
    END FIDE_BUSCAR_TIPO_TRANSACCION_FN;
            
    -- 8. Función Cantidad De Usuarios Por Estado
    
    FUNCTION FIDE_USUARIOS_POR_ESTADO_FN RETURN SYS_REFCURSOR IS
        V_CURSOR SYS_REFCURSOR;
    BEGIN
        OPEN V_CURSOR FOR
            SELECT E.NOMBRE AS NOMBRE_ESTADO,
                   COUNT(U.ID_USUARIO) AS CANTIDAD_USUARIOS
            FROM FIDE_ESTADO_TB E
            LEFT JOIN FIDE_USUARIO_TB U ON E.ID_ESTADO = U.ID_ESTADO
            WHERE E.NOMBRE IN ('ACTIVO', 'INACTIVO')
            GROUP BY E.NOMBRE
            ORDER BY E.NOMBRE;
        RETURN V_CURSOR;
    EXCEPTION
        WHEN OTHERS THEN
            RETURN NULL;
    END FIDE_USUARIOS_POR_ESTADO_FN;
    
    FUNCTION FIDE_PRESTAMOS_POR_ESTADO_FN RETURN SYS_REFCURSOR IS
        V_CURSOR SYS_REFCURSOR;
    BEGIN 
        OPEN V_CURSOR FOR
            SELECT E.NOMBRE AS NOMBRE_ESTADO,
                COUNT(P.ID_PRESTAMO) AS CANTIDAD_PRESTAMOS
            FROM FIDE_ESTADO_TB E
            LEFT JOIN FIDE_PRESTAMO_TB P ON E.ID_ESTADO = P.ID_ESTADO
            WHERE E.NOMBRE IN ('Completado', 'INACTIVO')
            GROUP BY E.NOMBRE
            ORDER BY E.NOMBRE;
        RETURN V_CURSOR;
    EXCEPTION
        WHEN OTHERS THEN
            RETURN NULL;
    END FIDE_PRESTAMOS_POR_ESTADO_FN;

    -- 9. Función Promedio De Montos De Ahorros

    FUNCTION FIDE_PROMEDIO_AHORROS_FN RETURN NUMBER IS
        CURSOR CURSOR_AHORROS IS
            SELECT SALDO_ACTUAL
            FROM FIDE_CUENTAS_AHORRO_TB
            WHERE ID_ESTADO = 1;
        V_SUMA      NUMBER := 0;
        V_CONTADOR  NUMBER := 0;
        V_PROMEDIO  NUMBER := 0;
    BEGIN
        FOR AHORRO IN CURSOR_AHORROS LOOP
            V_SUMA     := V_SUMA + NVL(AHORRO.SALDO_ACTUAL, 0);
            V_CONTADOR := V_CONTADOR + 1;
        END LOOP;
        IF V_CONTADOR > 0 THEN
            V_PROMEDIO := V_SUMA / V_CONTADOR;
            DBMS_OUTPUT.PUT_LINE('SUMA TOTAL AHORROS: ' || V_SUMA);
            DBMS_OUTPUT.PUT_LINE('CANTIDAD CUENTAS: '   || V_CONTADOR);
            DBMS_OUTPUT.PUT_LINE('PROMEDIO DE AHORROS: ' || V_PROMEDIO);
        ELSE
            DBMS_OUTPUT.PUT_LINE('NO HAY CUENTAS DE AHORRO ACTIVAS');
        END IF;
        RETURN V_PROMEDIO;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            DBMS_OUTPUT.PUT_LINE('NO SE ENCONTRARON AHORROS');
            RETURN 0;
        WHEN ZERO_DIVIDE THEN
            DBMS_OUTPUT.PUT_LINE('NO HAY DATOS PARA CALCULAR PROMEDIO');
            RETURN 0;
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('ERROR: ' || SQLERRM);
            RETURN -1;
    END FIDE_PROMEDIO_AHORROS_FN;

    -- 10. Función Usuarios Registrados En El Último Mes

    FUNCTION FIDE_USUARIOS_ULTIMO_MES_FN RETURN SYS_REFCURSOR IS
        V_CURSOR SYS_REFCURSOR;
    BEGIN
        OPEN V_CURSOR FOR
            SELECT U.NOMBRE,
                   U.APELLIDO_PATERNO,
                   U.APELLIDO_MATERNO,
                   D.FECHA_AFILIACION
            FROM FIDE_USUARIO_TB U
            JOIN FIDE_DATOS_ASOCIADOS_TB D
            ON U.ID_DATOS_ASOCIADOS = D.ID_DATOS_ASOCIADOS
            WHERE D.FECHA_AFILIACION >= ADD_MONTHS(SYSDATE, -1)
            ORDER BY D.FECHA_AFILIACION DESC;
        RETURN V_CURSOR;
    EXCEPTION
        WHEN OTHERS THEN
            RETURN NULL;
    END FIDE_USUARIOS_ULTIMO_MES_FN;

    -- 11. Función Top 5 Usuarios Con Más Transacciones

     FUNCTION FIDE_TOP5_USUARIOS_TRANSACCIONES_FN RETURN SYS_REFCURSOR IS
        V_CURSOR SYS_REFCURSOR;
    BEGIN
        OPEN V_CURSOR FOR
            SELECT * FROM (
                SELECT U.ID_USUARIO,
                       U.NOMBRE,
                       U.APELLIDO_PATERNO,
                       COUNT(T.ID_TRANSACCION) AS TOTAL_TRANSACCIONES
                FROM FIDE_USUARIO_TB U
                JOIN FIDE_TRANSACCION_TB T
                ON U.ID_USUARIO = T.ID_USUARIO
                GROUP BY U.ID_USUARIO, U.NOMBRE, U.APELLIDO_PATERNO
                ORDER BY TOTAL_TRANSACCIONES DESC
            ) WHERE ROWNUM <= 5;
        RETURN V_CURSOR;
    EXCEPTION
        WHEN OTHERS THEN
            RETURN NULL;
    END FIDE_TOP5_USUARIOS_TRANSACCIONES_FN;

    -- 12. Función Cantidad De Transacciones Por Usuario

    FUNCTION FIDE_CANTIDAD_TRANSACCIONES_USUARIO_FN(
        P_ID_USUARIO NUMBER
    ) RETURN NUMBER IS
        CURSOR CURSOR_TRANSACCIONES IS
            SELECT *
            FROM FIDE_TRANSACCION_TB
            WHERE ID_USUARIO = P_ID_USUARIO;
        V_CONTADOR NUMBER := 0;
    BEGIN
        FOR TRANS IN CURSOR_TRANSACCIONES LOOP
            V_CONTADOR := V_CONTADOR + 1;
        END LOOP;
        RETURN V_CONTADOR;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            DBMS_OUTPUT.PUT_LINE('USUARIO NO ENCONTRADO');
            RETURN 0;
        WHEN TOO_MANY_ROWS THEN
            DBMS_OUTPUT.PUT_LINE('ERROR DE DATOS');
            RETURN -1;
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('ERROR: ' || SQLERRM);
            RETURN -1;
    END FIDE_CANTIDAD_TRANSACCIONES_USUARIO_FN;

    -- 13. Función Usuarios Con Mayores Ahorros A Un Monto

   FUNCTION FIDE_USUARIOS_MAYOR_AHORRO_FN(
        P_MONTO NUMBER
    ) RETURN SYS_REFCURSOR IS
        V_CURSOR SYS_REFCURSOR;
    BEGIN
        OPEN V_CURSOR FOR
            SELECT U.NOMBRE,
                   U.APELLIDO_PATERNO,
                   A.SALDO_ACTUAL
            FROM FIDE_USUARIO_TB U
            JOIN FIDE_CUENTAS_AHORRO_TB A
            ON U.ID_USUARIO = A.ID_USUARIO
            WHERE A.SALDO_ACTUAL > P_MONTO;
        RETURN V_CURSOR;
    EXCEPTION
        WHEN OTHERS THEN
            RETURN NULL;
    END FIDE_USUARIOS_MAYOR_AHORRO_FN;

    -- 14. Función Historial De Transacciones Por Usuario

    FUNCTION FIDE_HISTORIAL_TRANSACCIONES_FN(
        P_ID_USUARIO NUMBER
    ) RETURN SYS_REFCURSOR IS
        V_CURSOR SYS_REFCURSOR;
    BEGIN
        OPEN V_CURSOR FOR
            SELECT ID_TRANSACCION,
                   FECHA_TRANSACCION,
                   MONTO_TOTAL
            FROM FIDE_TRANSACCION_TB
            WHERE ID_USUARIO = P_ID_USUARIO
            ORDER BY FECHA_TRANSACCION;
        RETURN V_CURSOR;
    EXCEPTION
        WHEN OTHERS THEN
            RETURN NULL;
    END FIDE_HISTORIAL_TRANSACCIONES_FN;

    -- 15. Función Reporte De Ahorros Por Usuario
    
    FUNCTION FIDE_REPORTE_AHORROS_USUARIO_FN(
        P_ID_USUARIO NUMBER
    ) RETURN SYS_REFCURSOR IS
        V_CURSOR SYS_REFCURSOR;
    BEGIN
        OPEN V_CURSOR FOR
            SELECT ID_AHORRO,
                   MONTO_APORTE,
                   SALDO_ACTUAL
            FROM FIDE_CUENTAS_AHORRO_TB
            WHERE ID_USUARIO = P_ID_USUARIO;
        RETURN V_CURSOR;
    EXCEPTION
        WHEN OTHERS THEN
            RETURN NULL;
    END FIDE_REPORTE_AHORROS_USUARIO_FN;


END ASEFIDE_PKG;



