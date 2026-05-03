classdef qadeqn < matlab.apps.AppBase

    % Properties that correspond to app components
    properties (Access = public)
        UIFigure            matlab.ui.Figure
        calculateButton     matlab.ui.control.Button
        aEditFieldLabel     matlab.ui.control.Label
        aEditField          matlab.ui.control.NumericEditField
        bEditFieldLabel     matlab.ui.control.Label
        bEditField          matlab.ui.control.NumericEditField
        cEditFieldLabel     matlab.ui.control.Label
        cEditField          matlab.ui.control.NumericEditField
        root1EditFieldLabel matlab.ui.control.Label
        root1EditField      matlab.ui.control.NumericEditField
        root2EditFieldLabel matlab.ui.control.Label
        root2EditField      matlab.ui.control.NumericEditField
        discriminantLabel   matlab.ui.control.Label
        statusLabel         matlab.ui.control.Label
        titleLabel          matlab.ui.control.Label
    end

    % Callbacks that handle component events
    methods (Access = private)

        % Button pushed function: calculateButton
        function calculateButtonPushed(app, event)
            a = app.aEditField.Value;
            b = app.bEditField.Value;
            c = app.cEditField.Value;

            % Input validation
            if a == 0
                app.statusLabel.Text = 'Error: coefficient a cannot be zero.';
                app.statusLabel.FontColor = [0.8 0 0];
                app.root1EditField.Value = 0;
                app.root2EditField.Value = 0;
                app.discriminantLabel.Text = 'Discriminant: N/A';
                return;
            end

            % Discriminant
            D = b^2 - 4*a*c;
            app.discriminantLabel.Text = sprintf('Discriminant D = %.4f', D);

            if D > 0
                % Two distinct real roots
                root1 = (-b + sqrt(D)) / (2*a);
                root2 = (-b - sqrt(D)) / (2*a);
                app.root1EditField.Value = root1;
                app.root2EditField.Value = root2;
                app.statusLabel.Text = 'Two distinct real roots.';
                app.statusLabel.FontColor = [0 0.5 0];

            elseif D == 0
                % One repeated real root
                root1 = -b / (2*a);
                app.root1EditField.Value = root1;
                app.root2EditField.Value = root1;
                app.statusLabel.Text = 'One repeated real root.';
                app.statusLabel.FontColor = [0 0 0.8];

            else
                % Complex roots — display real and imaginary parts
                realPart = -b / (2*a);
                imagPart = sqrt(-D) / (2*a);
                app.root1EditField.Value = realPart;
                app.root2EditField.Value = imagPart;
                app.statusLabel.Text = sprintf(...
                    'Complex roots: %.4f + %.4fi  /  %.4f - %.4fi', ...
                    realPart, imagPart, realPart, imagPart);
                app.statusLabel.FontColor = [0.8 0.4 0];
            end
        end

        % Clear button — reset all fields
        function clearFields(app)
            app.aEditField.Value = 0;
            app.bEditField.Value = 0;
            app.cEditField.Value = 0;
            app.root1EditField.Value = 0;
            app.root2EditField.Value = 0;
            app.discriminantLabel.Text = 'Discriminant: —';
            app.statusLabel.Text = 'Enter coefficients and press Calculate.';
            app.statusLabel.FontColor = [0.3 0.3 0.3];
        end
    end

    % Component initialization
    methods (Access = private)

        function createComponents(app)
            % UIFigure
            app.UIFigure = uifigure('Visible', 'off');
            app.UIFigure.Position = [100 100 640 520];
            app.UIFigure.Name = 'Quadratic Equation Solver';
            app.UIFigure.Color = [0.96 0.96 0.96];

            % Title label
            app.titleLabel = uilabel(app.UIFigure);
            app.titleLabel.Text = 'Quadratic Equation Solver  —  ax² + bx + c = 0';
            app.titleLabel.Position = [60 460 520 30];
            app.titleLabel.FontSize = 16;
            app.titleLabel.FontWeight = 'bold';
            app.titleLabel.HorizontalAlignment = 'center';

            % Coefficient a
            app.aEditFieldLabel = uilabel(app.UIFigure);
            app.aEditFieldLabel.HorizontalAlignment = 'right';
            app.aEditFieldLabel.Position = [130 400 80 22];
            app.aEditFieldLabel.Text = 'Coefficient a:';
            app.aEditFieldLabel.FontSize = 13;

            app.aEditField = uieditfield(app.UIFigure, 'numeric');
            app.aEditField.Position = [220 400 120 22];
            app.aEditField.FontSize = 13;

            % Coefficient b
            app.bEditFieldLabel = uilabel(app.UIFigure);
            app.bEditFieldLabel.HorizontalAlignment = 'right';
            app.bEditFieldLabel.Position = [130 360 80 22];
            app.bEditFieldLabel.Text = 'Coefficient b:';
            app.bEditFieldLabel.FontSize = 13;

            app.bEditField = uieditfield(app.UIFigure, 'numeric');
            app.bEditField.Position = [220 360 120 22];
            app.bEditField.FontSize = 13;

            % Coefficient c
            app.cEditFieldLabel = uilabel(app.UIFigure);
            app.cEditFieldLabel.HorizontalAlignment = 'right';
            app.cEditFieldLabel.Position = [130 320 80 22];
            app.cEditFieldLabel.Text = 'Coefficient c:';
            app.cEditFieldLabel.FontSize = 13;

            app.cEditField = uieditfield(app.UIFigure, 'numeric');
            app.cEditField.Position = [220 320 120 22];
            app.cEditField.FontSize = 13;

            % Calculate button
            app.calculateButton = uibutton(app.UIFigure, 'push');
            app.calculateButton.ButtonPushedFcn = ...
                createCallbackFcn(app, @calculateButtonPushed, true);
            app.calculateButton.Position = [160 260 130 32];
            app.calculateButton.Text = 'Calculate';
            app.calculateButton.FontSize = 13;
            app.calculateButton.FontWeight = 'bold';
            app.calculateButton.BackgroundColor = [0.2 0.5 0.8];
            app.calculateButton.FontColor = [1 1 1];

            % Clear button
            clearBtn = uibutton(app.UIFigure, 'push');
            clearBtn.ButtonPushedFcn = createCallbackFcn(app, @clearFields, true);
            clearBtn.Position = [310 260 100 32];
            clearBtn.Text = 'Clear';
            clearBtn.FontSize = 13;
            clearBtn.BackgroundColor = [0.7 0.7 0.7];

            % Discriminant label
            app.discriminantLabel = uilabel(app.UIFigure);
            app.discriminantLabel.Text = 'Discriminant: —';
            app.discriminantLabel.Position = [160 220 320 22];
            app.discriminantLabel.FontSize = 12;
            app.discriminantLabel.HorizontalAlignment = 'center';

            % Root 1
            app.root1EditFieldLabel = uilabel(app.UIFigure);
            app.root1EditFieldLabel.HorizontalAlignment = 'right';
            app.root1EditFieldLabel.Position = [80 170 80 22];
            app.root1EditFieldLabel.Text = 'Root 1 (x₁):';
            app.root1EditFieldLabel.FontSize = 13;

            app.root1EditField = uieditfield(app.UIFigure, 'numeric');
            app.root1EditField.Position = [170 170 140 22];
            app.root1EditField.Editable = false;
            app.root1EditField.FontSize = 13;

            % Root 2
            app.root2EditFieldLabel = uilabel(app.UIFigure);
            app.root2EditFieldLabel.HorizontalAlignment = 'right';
            app.root2EditFieldLabel.Position = [340 170 80 22];
            app.root2EditFieldLabel.Text = 'Root 2 (x₂):';
            app.root2EditFieldLabel.FontSize = 13;

            app.root2EditField = uieditfield(app.UIFigure, 'numeric');
            app.root2EditField.Position = [430 170 140 22];
            app.root2EditField.Editable = false;
            app.root2EditField.FontSize = 13;

            % Status label
            app.statusLabel = uilabel(app.UIFigure);
            app.statusLabel.Text = 'Enter coefficients and press Calculate.';
            app.statusLabel.Position = [60 110 520 40];
            app.statusLabel.FontSize = 12;
            app.statusLabel.FontColor = [0.3 0.3 0.3];
            app.statusLabel.HorizontalAlignment = 'center';
            app.statusLabel.WordWrap = 'on';

            % Footer
            footer = uilabel(app.UIFigure);
            footer.Text = 'App Development 1  ·  MRCET Aeronautical Engineering  ·  2022';
            footer.Position = [60 30 520 22];
            footer.FontSize = 10;
            footer.FontColor = [0.5 0.5 0.5];
            footer.HorizontalAlignment = 'center';

            app.UIFigure.Visible = 'on';
        end
    end

    % App creation and deletion
    methods (Access = public)

        function app = qadeqn
            createComponents(app)
            registerApp(app, app.UIFigure)
            if nargout == 0
                clear app
            end
        end

        function delete(app)
            delete(app.UIFigure)
        end
    end
end