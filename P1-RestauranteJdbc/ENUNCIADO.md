# P1 · La Tasca — RETO 4 (columna nueva) + validación

**Tiempo objetivo:** 25 minutos. Cronométrate.
**Puedes usar:** tus apuntes, tus repos (SimulacroEXSpring, ProductosJdbc...) y la chuleta de los 10 sitios.
**Sin ayuda** hasta que termines (salvo 10 minutos atascado en el mismo error).

## Qué se pide

1. Añade a la tabla `plato` una columna `calorias` (entero, obligatoria).
   Dale un valor a los 10 platos de ejemplo; al menos una bebida con 0.
2. En el listado `/platos`, muestra una columna **Calorías** entre Precio y Categoría.
3. En el formulario (alta y edición), añade el campo **Calorías** con estas validaciones:
   - vacío → *"Las calorías son obligatorias"*
   - negativo → *"Las calorías no pueden ser negativas"*
   - 0 es válido
4. Al editar un plato, el campo debe salir con sus calorías actuales. Al guardar
   (plato nuevo o editado), el valor debe guardarse y verse en el listado.
5. **Validación extra:** el nombre del plato debe tener entre 3 y 100 caracteres.
   Si no, *"El nombre debe tener entre 3 y 100 caracteres"*.

## Comprobaciones (hazlas TODAS a mano antes de dar por terminado)

- [ ] La app arranca sin errores.
- [ ] Alta con calorías vacías → primer mensaje. Con -5 → segundo mensaje. Con 0 → se guarda.
- [ ] Alta con nombre "Té" (2 letras) → mensaje del nombre.
- [ ] Editar *Paella mixta* → el campo sale con sus calorías; ponle 750 → en el listado sale 750.
- [ ] Crear un plato nuevo con 320 calorías → aparece en el listado con 320, sin errores.

## Al terminar, apunta

- Cuánto has tardado.
- Qué archivos has tocado y en qué orden.
- Dónde te has atascado.
