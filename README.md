# MineClone

Le plan : 
- Architecture
  - [ ] Tout mettre dans des classes propres
    - [ ] (Ré)-Apprendre à utiliser OpenGL
    - [x] Potentiellement Inputs à part
- Génération
  - [ ] Pseudo illimitée
    - [ ] Perlin Noise
      - [ ] Type de bloc dépendant de la hauteur
      - [ ] Génération en utilisant un genre de pointeur de texture pour la charger qu'une seule fois
      - [ ] Pour ce faire BlocID (0 = air; 1 = Dirt; 2 = Stone ...)
- Rendu
  - [ ] Potentiellement faire des ombres (Ray Traced ?)
  - [ ] Face Culling (autrement appeler ne pas rendre les faces des blocs qu'on ne voit pas, sur un chunk de 16*16*16(*4 par face de cube) on ne voit en réalité que maximum 16 faces en le regardant donc on va pas rendre 16²(*3 faces) blocs en plus, les drawCalls sont très cher en Performance, et opti tout ça, ça va être dur mggle)
  
