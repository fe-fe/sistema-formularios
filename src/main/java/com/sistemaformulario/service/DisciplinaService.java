import com.sistemaformulario.dao.CursoDAO;
import com.sistemaformulario.dto.DisciplinaDTO;
import com.sistemaformulario.entities.academico.Curso;
import com.sistemaformulario.entities.academico.Disciplina;

import java.util.List;

public class DisciplinaService {
    private DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
    private CursoDAO cursoDAO = new CursoDAO();

    public void criar(DisciplinaDTO dto) {
        Curso curso = cursoDAO.findById(dto.getCursoId());
        if (curso == null) throw new RuntimeException("Curso não encontrado");

        Disciplina disciplina = new Disciplina();
        disciplina.setNome(dto.getNome());
        disciplina.setCurso(curso);
        disciplinaDAO.create(disciplina);
    }

    // Método auxiliar para listar disciplinas de um curso (útil no combo box)
    public List<Disciplina> listar() { return disciplinaDAO.findAll(); }
}